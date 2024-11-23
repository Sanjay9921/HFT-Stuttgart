# AISimplification/aisimplify.py

from flask import jsonify
from transformers import T5Tokenizer, T5ForConditionalGeneration

# Load the model and tokenizer
model_name = "weijiahaha/t5-small-summarization" # A model from T5 small'
max_target_length = 100  # Set a reasonable max length for the output
tokenizer = T5Tokenizer.from_pretrained(model_name)
model = T5ForConditionalGeneration.from_pretrained(model_name)

# TODO: Add retry if output is empty
def simplify_text(data):
    input_text = f"simplify the sentence in easy words: {data['text']}"
    
    # Example 
    # input_text = f"simplify the sentence in easy words: Despite the heavy rain that poured down throughout the night, which caused flooding in several areas of the city, the festival organizers decided to proceed with the event as planned, believing that the enthusiasm of the attendees would not be dampened."

    input_ids = tokenizer(input_text, 
                          return_tensors="pt").input_ids
    # Run the model
    outputs = model.generate(input_ids, max_new_tokens=max_target_length)
    
    # Decode the output
    simplified_text = tokenizer.decode(outputs[0], skip_special_tokens=True)
    print(f"Simplified Text: {simplified_text}") 
    return jsonify({"simplified_text": simplified_text})
