import pytest
from database_ops.repositories.book_versions_repository import add_book_version, get_versions_by_book_id, delete_book_version
from database_ops.models.book import Book
from database_ops.models.processing_step import ProcessingStep
from database_ops.models.book_versions import BookVersion

def test_add_book_version(session):
    book = Book(title="Versioned Book", author="Author A")
    step = ProcessingStep(step_name="Editing")
    session.add(book)
    session.add(step)
    session.commit()
    
    version = add_book_version(book.book_id, step.step_id, content="Initial Content")
    
    assert version.version_id is not None
    assert version.content == "Initial Content"

def test_get_versions_by_book_id(session):
    book = Book(title="Versioned Book", author="Author B")
    step1 = ProcessingStep(step_name="Step 1")
    step2 = ProcessingStep(step_name="Step 2")
    session.add_all([book, step1, step2])
    session.commit()
    
    add_book_version(book.book_id, step1.step_id, content="Content A")
    add_book_version(book.book_id, step2.step_id, content="Content B")
    
    versions = get_versions_by_book_id(book.book_id)
    assert len(versions) == 2
    assert versions[0].content == "Content A"
    assert versions[1].content == "Content B"

def test_delete_version(session):
    book = Book(title="To Delete Version", author="Author C")
    step = ProcessingStep(step_name="Review")
    session.add_all([book, step])
    session.commit()

    version = add_book_version(book.book_id, step.step_id, content="Delete This")
    
    # Call delete_book_version, which will return the deleted version object
    deleted_version = delete_book_version(version.version_id)

    # Check if the deleted_version is the same as the one we created
    assert deleted_version is not None  # Ensure that the version is deleted (not None)
    assert deleted_version.content == version.content  # Ensure the deleted version matches the original one

    # Verify that the version is indeed deleted from the database
    assert BookVersion.query.get(version.version_id) is None  # Ensure the version is no longer in the database

