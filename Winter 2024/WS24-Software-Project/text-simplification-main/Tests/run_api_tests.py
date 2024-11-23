import subprocess

collection_path = r"Postman_Collection/Vector_Text_Simplification.postman_collection.json"
report_path = r"Postman_Collection/report.html"
# Run the Newman command using subprocess
def run_postman_collection():
    try:
        result = subprocess.run(
            ["newman", "run", collection_path, 
             "--reporters", "html",
             "--reporter-html-export", report_path],
            check=True,
            capture_output=True,
            text=True,
            shell=True,
            encoding="utf-8"
        )
    except subprocess.CalledProcessError as e:
        print("An error occurred during testing:", e.stderr)

if __name__ == "__main__":
    run_postman_collection()
