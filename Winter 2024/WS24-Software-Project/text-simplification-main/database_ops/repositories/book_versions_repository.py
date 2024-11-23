from database_ops.models.book_versions import BookVersion
from database_ops.utils.db import db

def add_book_version(book_id, step_id, content, session):
    """
    Repository function to create a new book version.
    """
    new_version = BookVersion(book_id=book_id, step_id=step_id, content=content)
    session.add(new_version)
    return new_version

def get_versions_by_book_id(book_id):
    """
    Repository function to retrieve all versions for a given book.
    """
    versions = BookVersion.query.filter_by(book_id=book_id).all()
    return versions

def get_version_by_id(version_id):
    """
    Retrieve a book version by its version ID.
    """
    return BookVersion.query.get(version_id)

def get_versions_by_step_id(step_id):
    """
    Retrieve all versions associated with a specific processing step.
    """
    return BookVersion.query.filter_by(step_id=step_id).all()

def update_book_version(version_id, content=None):
    """
    Update the content of a book version by its ID.
    """
    version = get_version_by_id(version_id)
    if version and content is not None:
        version.content = content
        db.session.commit()
    return version

def delete_book_version(version_id):
    """
    Delete a book version by its ID.
    """
    version = get_version_by_id(version_id)
    if version:
        db.session.delete(version)
        db.session.commit()
    return version