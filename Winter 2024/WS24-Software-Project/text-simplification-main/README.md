
# Text Simplification Application

This project is a text simplification application that takes eBooks from the Gutenberg library as input, processes them to simplify complex sentences and vocabulary, and outputs the simplified text.

## Overview

The Text Simplification Application aims to make reading complex texts more accessible by simplifying sentence structure and vocabulary. The app fetches eBooks from the Gutenberg library, cleans the text, segments it, applies AI-based text simplification, and generates simplified output.

### Key Modules:
1. **Data Ingestion:** Fetch and parse eBooks from Gutenberg API.
2. **Preprocessing:** Clean and segment raw text.
3. **Text Simplification:** Apply AI models to simplify text.
4. **Post-Processing:** Format the simplified text for readability.
5. **Output Generation:** Export simplified eBooks in different formats.

## Installation

1. Clone the repository:
   ```bash
   git clone https://gitlab.rz.hft-stuttgart.de/vector-software-project/text-simplification.git
   ```
3. Set up a Python virtual environment:
   ```bash
   python -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   ```
4. Install the required dependencies:
   ```bash
   pip install -r requirements.txt
   ```

## Usage

1. **Run the Application**:
   To run the text simplification pipeline on a Gutenberg eBook:
   ```bash
   python main.py
   ```
Access it on http://127.0.0.1:5000

2. **Database Setup**
- Create .env file in the same directory as the ``main.py``
   ```
   # .env file
   # Database configuration
   DB_USERNAME=<<YOUR_USERNAME>>
   DB_PASSWORD=<<YOUR_PASSWORD>>
   DB_HOST=localhost
   DB_NAME=gutenberg_db
   ```


   # .env file for running in docker
   DB_USERNAME=root
   DB_PASSWORD=root
   DB_HOST='host.docker.internal' # 'docker.for.mac.host.internal' for mac
   DB_NAME=test

- Windows
   * Visit https://dev.mysql.com/downloads/windows/installer/8.0.html
   * Download the MySQL 8.0.40 .msi for Windows (306.4M) file
   * This is the community edition
   * After setting up the database password, update the ``.env`` file you created in step (1)

- Creating the local db

   ```
   create database gutenberg_db;
   show databases;
   ```
   * 

- Docker Command for creating Image and Running the container
   
   * Pre-requirements: Must have Docker Installed and Open Docker since it starts Docker Demon
   1. docker build -t gpt-neo-flask-api . (In place of gpt-neo-flask-api you can keep any <IMAGE_NAME>)
   2. docker run --add-host host.docker.internal:host-gateway -p 5000:5000 <IMAGE_NAME>


## Contributing

We welcome contributions! Please follow these steps:

1. Clone the repository.
2. Create a new feature branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m "Add feature"`).
4. Push to the branch (`git push origin feature-name`).
5. Open a Pull Request.
