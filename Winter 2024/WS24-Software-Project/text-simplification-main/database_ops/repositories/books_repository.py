from database_ops.models.book import Book
from database_ops.utils.db import db

def add_book(title, session, author=None, language=None):
    """
    Repository function to create a new book.
    """
    new_book = Book(title=title, author=author, language=language)
    session.add(new_book)
    return new_book

def get_all_books():
    """
    Repository function to retrieve all books.
    """
    return Book.query.all()

def get_book_by_id(book_id):
    """
    Retrieve a book by its ID.
    """
    return Book.query.get(book_id)

def get_books_by_author(author):
    """
    Retrieve books by a specific author.
    """
    return Book.query.filter_by(author=author).all()

def update_book(book_id, title=None, author=None, language=None):
    """
    Update book details by book ID.
    """
    book = get_book_by_id(book_id)
    if book:
        if title is not None:
            book.title = title
        if author is not None:
            book.author = author
        if language is not None:
            book.language = language
        db.session.commit()
    return book

def delete_book(book_id):
    """
    Delete a book by its ID.
    """
    book = get_book_by_id(book_id)
    if book:
        db.session.delete(book)
        db.session.commit()
    return book