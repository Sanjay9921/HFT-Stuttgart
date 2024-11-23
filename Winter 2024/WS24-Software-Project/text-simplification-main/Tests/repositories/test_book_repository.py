import pytest
from database_ops.repositories.books_repository import add_book, get_all_books, get_book_by_id, update_book, delete_book
from database_ops.models.book import Book

def test_add_book(session):
    new_book = add_book(title="Test Book", author="Author A", language="English")
    assert new_book.book_id is not None
    assert new_book.title == "Test Book"
    assert new_book.author == "Author A"
    assert new_book.language == "English"

def test_get_all_books(session):
    add_book(title="Book A", author="Author A", language="English")
    add_book(title="Book B", author="Author B", language="French")
    
    books = get_all_books()
    assert len(books) == 2
    assert books[0].title == "Book A"
    assert books[1].title == "Book B"

def test_get_book_by_id(session):
    book = add_book(title="Single Book", author="Author A", language="German")
    fetched_book = get_book_by_id(book.book_id)
    
    assert fetched_book is not None
    assert fetched_book.title == "Single Book"
    assert fetched_book.language == "German"

def test_update_book(session):
    book = add_book(title="Old Title", author="Author A", language="English")
    updated_book = update_book(book.book_id, title="New Title")
    
    assert updated_book.title == "New Title"
    assert updated_book.author == "Author A"

def test_delete_book(session):
    book = add_book(title="To Delete", author="Author A", language="English")
    
    # Call delete_book, which will return the deleted book object
    deleted_book = delete_book(book.book_id)

    # Check if the deleted_book is the same as the one we created
    assert deleted_book is not None  # Ensure that the book is deleted (not None)
    assert deleted_book.title == book.title  # Ensure the deleted book matches the original one

    # Verify that the book is indeed deleted from the database
    assert Book.query.get(book.book_id) is None  # Ensure the book is no longer in the database

