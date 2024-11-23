from flask import Flask, request, jsonify
from AISimplification.aisimplify import simplify_text
from Segmentation.text_segmentation import TextSegmentation
# 1. Libraries

## 1.1 Flask
from flask_restx import Api, Resource
from flask_restx import fields

## 1.2 Ingestion
from Ingestion.Gutenberg.gutenberg_client import GutenbergClient
from Ingestion.Gutenberg.book_searcher import BookSearcher

## 1.3 Database
from database_ops.models.processing_step import ProcessingStep
from database_ops.routes import books_bp, processing_steps_bp, book_versions_bp  # Import blueprints
from database_ops.utils.db import db  # Import the database object
from config import Config  # Import app configuration

# 2. Application Codec

## 2.1 Flask Object
app = Flask(__name__)

api = Api(app, version="1.0", title="Text Simplification API",
          description="API for processing Books with Swagger UI", doc="/swagger")


## 2.2 Database Setup

### 2.2.1 Load configuration file
app.config.from_object(Config)  # Load config
db.init_app(app)  # Initialize SQLAlchemy with the app

### 2.2.2 Register the APIs
app.register_blueprint(books_bp, url_prefix='/books')
app.register_blueprint(processing_steps_bp, url_prefix='/processing-steps')
app.register_blueprint(book_versions_bp, url_prefix='/book-versions')

with app.app_context():
    db.create_all()
    if not ProcessingStep.query.first():
        steps = [ProcessingStep(step_name="Ingestion"), ProcessingStep(step_name="Segmentation")]
        db.session.add_all(steps)
        db.session.commit()

# Define a simple data model for Swagger documentation
book_model = api.model('Book', {
    'id': fields.Integer(readOnly=True, description='The book identifier'),
    'title': fields.String(required=True, description='The title of the book'),
    'author': fields.String(required=True, description='The author of the book')
})

## 2.3 Routes
# Documentation for Swagger UI (example route)
@api.route('/gutenberg/<string:title>')
class GutenbergResource(Resource):
    def get(self, title):
        """Fetch book by title from Gutenberg"""
        client = GutenbergClient()
        searcher = BookSearcher(client)
        book = searcher.search_books_by_title(title)
        return searcher.fetch_and_print_html_content(book)

    @app.route('/')
    def hello_world():
        return 'Hello, Text Simplification!'

    @app.route('/simplify', methods=['POST'])
    def simplify():
        data = request.get_json()
        return simplify_text(data)

    @app.route('/gutenberg/<title>')
    def get_data(title):
        client = GutenbergClient()
        searcher = BookSearcher(client)
        book = searcher.search_books_by_title(title)
        cleaned_text = searcher.fetch_and_print_html_content(book)
        segmented_text = TextSegmentation.segment_text(cleaned_text)
        return segmented_text

    @app.errorhandler(400)
    def handle_bad_request(error):
        return jsonify(error=str(error)), 400

    @app.errorhandler(404)
    def handle_not_found(error):
        return jsonify(error=str(error)), 404

    @app.errorhandler(500)
    def handle_internal_server_error(error):
        return jsonify(error=str(error)), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
