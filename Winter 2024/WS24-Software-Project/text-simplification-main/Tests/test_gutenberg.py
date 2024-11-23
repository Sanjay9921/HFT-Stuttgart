import unittest
from unittest.mock import patch, MagicMock
from Ingestion.Gutenberg.gutenberg_client import GutenbergClient
from Ingestion.Gutenberg.book_searcher import BookSearcher, Book
from flask import Flask, abort
import requests

app = Flask(__name__)

class TestGutenbergClient(unittest.TestCase):

    @patch('requests.get')
    def test_search_books_success(self, mock_get):
        mock_response = MagicMock()
        mock_response.json.return_value = {
            'results': [
                {
                    "id": 123,
                    "title": "title of book",
                    "authors": [
                        {
                        "name": "author's name",
                        "birth_year": 1111,
                        "death_year": 2222
                        }
                    ],
                    "translators": [],
                    "subjects": [
                        "Subject 1 -- Drama",
                        "Subject 2 -- NoDrama",
                        "Character1 (Fictitious character) -- Drama",
                        "Tragedies",
                        "Someting else"
                    ],
                    "bookshelves": [
                        "Browsing: Fiction",
                        "Browsing: Literature",
                        "Browsing: Performing Arts/Film"
                    ],
                    "languages": [
                        "en"
                    ],
                    "copyright": "boolean",
                    "media_type": "Text",
                    "formats": {
                        "text/html": "https://www.gutenberg.org/ebooks/'id'.html.images",
                        "application/epub+zip": "https://www.gutenberg.org/ebooks/'id'.epub3.images",
                        "application/x-mobipocket-ebook": "https://www.gutenberg.org/ebooks/'id'.kf8.images",
                        "application/rdf+xml": "https://www.gutenberg.org/ebooks/'id'.rdf",
                        "image/jpeg": "https://www.gutenberg.org/cache/epub/'id'/pg'id'.cover.medium.jpg",
                        "text/plain; charset=us-ascii": "https://www.gutenberg.org/ebooks/'id'.txt.utf-8",
                        "application/octet-stream": "https://www.gutenberg.org/cache/epub/'id'/pg'id'-h.zip"
                    },
                    "download_count": 1111
                }
            ]
        }
        mock_response.raise_for_status = MagicMock()
        mock_get.return_value = mock_response

        client = GutenbergClient()
        result = client.search_books("Test Book")

        self.assertEqual(len(result), 1)
        self.assertEqual(result[0]['title'], 'Test Book')

    @patch('requests.get')
    def test_search_books_not_found(self, mock_get):
        mock_response = MagicMock()
        mock_response.json.return_value = {'results': []}
        mock_response.raise_for_status = MagicMock()
        mock_get.return_value = mock_response

        client = GutenbergClient()

        with patch('Ingestion.Gutenberg.gutenberg_client.abort') as mock_abort:
            client.search_books("Non-existent Book")
            mock_abort.assert_called_once_with(404, description="No books found with 'Non-existent Book'.")

    @patch('requests.get')
    def test_search_books_request_exception(self, mock_get):
        mock_get.side_effect = requests.exceptions.RequestException("Error")

        client = GutenbergClient()

        with patch('Ingestion.Gutenberg.gutenberg_client.abort') as mock_abort:
            client.search_books("Non-existent Book")
            mock_abort.assert_called_once_with(500, description="External API request failed: Error")

    @patch('requests.get')
    def test_fetch_book_content_success(self, mock_get):
        mock_response = MagicMock()
        mock_response.text = '<html><body>Book content</body></html>'
        mock_response.raise_for_status = MagicMock()
        mock_get.return_value = mock_response

        client = GutenbergClient()
        result = client.fetch_book_content("http://example.com/book.html")

        self.assertIn('Book content', result)

    @patch('requests.get')
    def test_fetch_book_content_request_exception(self, mock_get):
        mock_get.side_effect = requests.exceptions.RequestException("Error")

        client = GutenbergClient()

        with patch('Ingestion.Gutenberg.gutenberg_client.abort') as mock_abort:
            client.fetch_book_content("http://example.com/book.html")
            mock_abort.assert_called_once_with(500, description="External API request failed: Error")


class TestBookSearcher(unittest.TestCase):
    @patch('Ingestion.Gutenberg.gutenberg_client.GutenbergClient.search_books')
    @patch('Ingestion.Gutenberg.gutenberg_client.GutenbergClient.fetch_book_content')
    def test_search_books_by_title_success(self, mock_fetch_content, mock_search_books):
        mock_search_books.return_value = [
            {
                'title': 'Test Book',
                'formats': {
                    'text/html': 'http://example.com/book.html'
                }
            }
        ]

        mock_fetch_content.return_value = '<html><body>Book content</body></html>'

        client = GutenbergClient()
        searcher = BookSearcher(client)
        book = searcher.search_books_by_title("Test Book")

        self.assertEqual(book.title, 'Test Book')
        self.assertEqual(book.html_link, 'http://example.com/book.html')

    @patch('Ingestion.Gutenberg.gutenberg_client.GutenbergClient.search_books')
    def test_search_books_by_title_no_html_link(self, mock_search_books):
        mock_search_books.return_value = [
            {
                'title': 'Test Book',
                'formats': {}
            }
        ]

        client = GutenbergClient()
        searcher = BookSearcher(client)

        with patch('Ingestion.Gutenberg.book_searcher.abort') as mock_abort:
            searcher.search_books_by_title("Test Book")
            mock_abort.assert_called_once_with(404, description="No HTML version available for 'Test Book'.")

    @patch('Ingestion.Gutenberg.gutenberg_client.GutenbergClient.fetch_book_content')
    def test_fetch_and_print_html_content(self, mock_fetch_content):
        mock_fetch_content.return_value = '<html><body>Book content</body></html>'

        client = GutenbergClient()
        searcher = BookSearcher(client)
        book = Book("Test Book", "http://example.com/book.html")
        content = searcher.fetch_and_print_html_content(book)

        self.assertIn('Book content', content)

if __name__ == '__main__':
    unittest.main()