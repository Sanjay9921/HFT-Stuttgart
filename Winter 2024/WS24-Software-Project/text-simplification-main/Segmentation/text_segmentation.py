import re
import spacy
from spacy.language import Language

class TextSegmentation:
    def segment_text(text):
        nlp = spacy.load("en_core_web_sm") #maybe useless even with pipeline
        # nlp.add_pipe("custom_sentence_segmentation", before="parser")

        paragraphs = re.split(r'\n\s*\n', text)  # Splits on double newlines
        segmented_text = []

        for paragraph in paragraphs:
            paragraph = paragraph.strip()
            doc = nlp(paragraph)  # Apply spaCy NLP processing
            sentences = [sent.text.strip() for sent in doc.sents]
            segmented_text.append(sentences)

        return segmented_text


#     # Example for a custom function. In this case for sentence segmentation to handle abbreviations

#     # @Language.component("custom_sentence_segmentation")
#     # def custom_sentence_segmentation(doc):
#     #     for token in doc[:-1]:
#     #         if token.text in {"Dr.", "e.g.", "i.e.", "Mr.", "Mrs."}:
#     #             doc[token.i + 1].is_sent_start = False
#     #         if token.text.isupper() and len(token.text) == 1 and doc[token.i + 1].text == ".":
#     #             doc[token.i + 2].is_sent_start = False
#     #     return doc