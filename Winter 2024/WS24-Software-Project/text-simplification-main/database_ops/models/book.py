from database_ops.utils.db import db

class Book(db.Model):
    __tablename__ = 'Books'

    book_id = db.Column(db.Integer, primary_key=True)
    title = db.Column(db.String(255), nullable=False)
    author = db.Column(db.String(255))
    language = db.Column(db.String(50))
    created_at = db.Column(db.DateTime, server_default=db.func.now())

    def as_dict(self):
        return {
            'book_id': self.book_id,
            'title': self.title,
            'author': self.author,
            'language': self.language,
            'created_at': self.created_at.isoformat()
        }
