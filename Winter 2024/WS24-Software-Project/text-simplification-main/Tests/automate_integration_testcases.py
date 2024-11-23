import requests
import unittest

class TestGutenbergAPI(unittest.TestCase):
    def setUp(self):
        """This method runs before each test"""
        self.base_url = "http://127.0.0.1:5000/gutenberg/"
        self.book_title = "The Aab"
        self.url = f"{self.base_url}{self.book_title.replace(' ', '%20')}"
    
    def test_get_book_details(self):
        """Test to fetch book details by title"""
        response = requests.get(self.url)

        # Assert that the status code is 200 (OK)
        self.assertEqual(response.status_code, 200, f"Failed to fetch the book. Status Code: {response.status_code}")

        try:
            # Try to parse the response as JSON
            response_json = response.json()
            self.assertIsInstance(response_json, dict, "Response should be a JSON object.")
            print("Response JSON:", response_json)
            
            # Optional: Additional checks based on expected structure
            self.assertIn('title', response_json, "Response JSON should contain 'title'.")
            self.assertIn('html_link', response_json, "Response JSON should contain 'html_link'.")
        
        except requests.exceptions.JSONDecodeError:
            # If the response is not JSON, print the raw response text for debugging
            self.fail(f"Response is not in JSON format. Raw response text: {response.text}")
        
    def test_empty_response(self):
        """Test handling empty responses"""
        response = requests.get(self.url)
        self.assertEqual(response.status_code, 200)
        
        if response.text == "":
            self.fail("Received an empty response.")

    def test_invalid_title(self):
        """Test fetching book with an invalid title"""
        invalid_title = "Nonexistent Book"
        invalid_url = f"{self.base_url}{invalid_title.replace(' ', '%20')}"
        response = requests.get(invalid_url)

        # Check that the status code indicates failure (404 Not Found)
        self.assertEqual(response.status_code, 404, f"Expected 404 Not Found, but got {response.status_code}.")

        try:
            response_json = response.json()
            self.assertIn("error", response_json, "Expected error message in response.")
            print("Error Response JSON:", response_json)

        except requests.exceptions.JSONDecodeError:
            self.fail("Response is not in JSON format. Raw response text: {response.text}")

if __name__ == "__main__":
    unittest.main()
