import requests
from flask import abort

class GutenbergClient:

    BASE_URL = "https://gutendex.com/books/"

    def search_books(self, title):
        try:
            search_url = f"{self.BASE_URL}?search={title.replace(' ', '%20')}"
            response = requests.get(search_url)
            response.raise_for_status()
            books = response.json().get('results', [])

            if not books:
                abort(404, description=f"No books found with '{title}'.")

            return books
        
        except requests.exceptions.RequestException as e:
            abort(500, description=f"External API request failed: {e}")

    def fetch_book_content(self, html_url):
        try:
            response = requests.get(html_url)
            response.raise_for_status()
            return response.text
        except requests.exceptions.RequestException as e:
            abort(500, description=f"External API request failed: {e}")