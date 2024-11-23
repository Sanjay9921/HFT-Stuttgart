import os
import sys
import pytest
from main import app  # Import the Flask app instance from main.py
from database_ops.utils.db import db
from sqlalchemy.orm import scoped_session, sessionmaker

@pytest.fixture(scope='module')
def test_app():
    # Override config for testing
    app.config['TESTING'] = True
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///:memory:'

    # Set up database tables within app context
    with app.app_context():
        db.create_all()  # Create tables
        yield app  # Provide the app for testing
        db.session.remove()
        db.drop_all()  # Drop tables after tests

@pytest.fixture(scope='function')
def session(test_app):
    """Creates a new database session for a test."""
    
    # Create a new session using scoped_session and sessionmaker
    connection = db.engine.connect()
    transaction = connection.begin()

    # Use a scoped session with this connection
    session_factory = sessionmaker(bind=connection)
    scoped_session_instance = scoped_session(session_factory)
    
    # Assign the scoped session to db.session
    db.session = scoped_session_instance

    yield scoped_session_instance  # Test runs here

    # Roll back the transaction after the test
    transaction.rollback()
    connection.close()
    scoped_session_instance.remove()  # Ensure that the session is removed
