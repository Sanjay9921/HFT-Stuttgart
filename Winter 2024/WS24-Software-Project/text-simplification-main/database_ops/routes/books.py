from flask import Blueprint, request, jsonify
from database_ops.models.book import Book
from database_ops.utils.db import db
from database_ops.repositories.books_repository import *  # Import repository functions


books_bp = Blueprint('books', __name__)

@books_bp.route('/', methods=['POST'])
def create_book_route():
    data = request.get_json()
    new_book = add_book(data['title'], data.get('author'), data.get('language'))
    return jsonify({"message": "Book created successfully", "book": new_book.as_dict()}), 201

@books_bp.route('/', methods=['GET'])
def get_books_route():
    books = get_all_books()
    return jsonify([book.as_dict() for book in books])

@books_bp.route('/<int:book_id>', methods=['GET'])
def get_book_by_id_route(book_id):
    book = get_book_by_id(book_id)
    if book:
        return jsonify(book.as_dict())
    return jsonify({"error": "Book not found"}), 404

@books_bp.route('/author/<string:author>', methods=['GET'])
def get_books_by_author_route(author):
    books = get_books_by_author(author)
    return jsonify([book.as_dict() for book in books])

@books_bp.route('/<int:book_id>', methods=['PUT'])
def update_book_route(book_id):
    data = request.get_json()
    updated_book = update_book(book_id, data.get('title'), data.get('author'), data.get('language'))
    if updated_book:
        return jsonify({"message": "Book updated successfully", "book": updated_book.as_dict()})
    return jsonify({"error": "Book not found"}), 404

@books_bp.route('/<int:book_id>', methods=['DELETE'])
def delete_book_route(book_id):
    deleted_book = delete_book(book_id)
    if deleted_book:
        return jsonify({"message": "Book deleted successfully"})
    return jsonify({"error": "Book not found"}), 404
