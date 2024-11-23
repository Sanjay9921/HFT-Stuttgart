from bs4 import BeautifulSoup
from Ingestion.Gutenberg.gutenberg_client import GutenbergClient
from flask import abort
from database_ops.repositories.books_repository import add_book,get_books_by_author
from database_ops.repositories.book_versions_repository import add_book_version
from sqlalchemy.orm import sessionmaker
from database_ops.utils.db import db
from sqlalchemy.exc import SQLAlchemyError

class Book:
    def __init__(self, title,author, html_link, book_language):
        self.title = title
        self.author = author
        self.html_link = html_link
        self.book_language = book_language

    def __str__(self):
        return f"{self.title} - {self.html_link}"


class BookSearcher:
    def __init__(self, client: GutenbergClient):
        self.client = client

    def search_books_by_title(self, title):
        books = self.client.search_books(title)

        book_data = books[0]
        book_title = book_data.get("title")
        book_author = book_data.get("authors")[0]['name']
        book_language = book_data.get("languages")[0]
        download_links = book_data.get("formats", {})
        html_link = download_links.get("text/html")
        if not html_link:
            abort(404, description=f"No HTML version available for '{book_title}'.")

        return Book(book_title,book_author, html_link, book_language)

    def fetch_and_print_html_content(self, book: Book):
        html_content = self.client.fetch_book_content(book.html_link)
        soup = BeautifulSoup(html_content, "html.parser")
        book_content = soup.find("body") or soup
        full_text = book_content.get_text()
        
        self.insert_into_db(book, full_text)
        
        text_sections = full_text.split("***")
        primary_text_section = text_sections[2]
        cleaned_main_text = primary_text_section.strip()
        return cleaned_main_text
    
    def insert_into_db(self, book, book_text):
        existing_book = get_books_by_author(book.author)

        if not existing_book:
            try:
                book_model = add_book(book.title,db.session, book.author, book.book_language)
                db.session.flush()

                add_book_version(book_model.book_id, 1, book_text, db.session)
                db.session.flush()

                db.session.commit()
                return True
            except SQLAlchemyError as e:
                abort(500, description=f"Failed to insert into database: {e}")
                db.session.rollback()
                return False
            finally:
                db.session.remove()
