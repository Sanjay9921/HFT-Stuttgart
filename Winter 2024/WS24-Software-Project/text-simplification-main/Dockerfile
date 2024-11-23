# Use a base image with Python 3.11
FROM python:3.11-slim

# Set the working directory
WORKDIR /app

# Copy the requirements file into the container
COPY requirements.txt /app/requirements.txt

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt --extra-index-url https://download.pytorch.org/whl/cpu
RUN python -m spacy download en_core_web_sm

# Copy the entire application into the container
COPY . /app

# RUN apt-get update \
#     && apt-get install -y \
#     ffmpeg \
#     libsm6 \
#     libxext6 \
#     && rm -rf /var/lib/apt/lists/*

# Expose port 5000
EXPOSE 5000

# Run the Flask server
CMD ["python", "main.py"]
