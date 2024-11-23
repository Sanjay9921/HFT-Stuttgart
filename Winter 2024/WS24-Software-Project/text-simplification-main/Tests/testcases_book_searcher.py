import pytest
from unittest.mock import Mock
from werkzeug.exceptions import NotFound
from Ingestion.Gutenberg.book_searcher import BookSearcher, Book
from Ingestion.Gutenberg.gutenberg_client import GutenbergClient

@pytest.fixture
def mock_client():
    """Fixture to create a mock of GutenbergClient."""
    return Mock(spec=GutenbergClient)

@pytest.fixture
def book_searcher(mock_client):
    """Fixture to create a BookSearcher instance with the mocked client."""
    return BookSearcher(client=mock_client)

def test_search_books_by_title_no_html_link(book_searcher, mock_client):
    """Test search_books_by_title when no HTML link is available."""
    # Mock response without 'text/html' format
    mock_client.search_books.return_value = [
        {
            'title': 'Pride and Prejudice',
            'formats': {}  # No 'text/html' format link here
        }
    ]

    # Test that search_books_by_title raises NotFound when no HTML link is found
    with pytest.raises(NotFound) as exc_info:
        book_searcher.search_books_by_title('Pride and Prejudice')

    # Validate that the exception type and message are as expected
    assert exc_info.type == NotFound
    assert "No HTML version available for 'Pride and Prejudice'." in str(exc_info.value)


def test_search_books_by_title_successful(book_searcher, mock_client):
    """Test search_books_by_title when a valid HTML link is available."""
    # Mock response with 'text/html' format
    mock_client.search_books.return_value = [
        {
            'title': 'Pride and Prejudice',
            'formats': {
                'text/html': 'https://example.com/pride_and_prejudice.html'
            }
        }
    ]

    # Test that search_books_by_title returns the correct book
    book = book_searcher.search_books_by_title('Pride and Prejudice')
    assert book.title == 'Pride and Prejudice'
    assert book.html_link == 'https://example.com/pride_and_prejudice.html'

def test_search_books_by_title_no_results(book_searcher, mock_client):
    """Test search_books_by_title when there are no search results."""
    # Mock an empty response from GutenbergClient
    mock_client.search_books.return_value = []

    # Test that search_books_by_title raises NotFound when no books are found
    with pytest.raises(NotFound) as exc_info:
        book_searcher.search_books_by_title('Nonexistent Book')

    # Validate exception message
    assert exc_info.type == NotFound
    assert "No books found with title 'Nonexistent Book'." in str(exc_info.value)

def test_search_books_by_title_api_failure(book_searcher, mock_client):
    """Test search_books_by_title when GutenbergClient encounters an API failure."""
    # Simulate an API request failure in GutenbergClient
    mock_client.search_books.side_effect = Exception("API request failed")

    # Test that search_books_by_title raises an internal server error when API fails
    with pytest.raises(Exception) as exc_info:
        book_searcher.search_books_by_title('Some Book')

    # Validate the exception type and message
    assert "API request failed" in str(exc_info.value)
