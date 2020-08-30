from utils import process_tweet
import torch
from transformers import pipeline, AutoTokenizer, AutoModelForSequenceClassification

tokenizer = AutoTokenizer.from_pretrained("model/checkpoint-10608-epoch-3")

model = AutoModelForSequenceClassification.from_pretrained("model/checkpoint-10608-epoch-3", from_tf=False)


nlp=pipeline("sentiment-analysis", model=model, tokenizer=tokenizer)



# print(nlp(process_tweet("seni öldürürüm")))


def is_this_hate_message(message):
        response = nlp(process_tweet(message))
        if (response[0]['label'] != 'LABEL_0'):
                return True
        return False

if __name__ == '__main__':
	print(is_this_hate_message("seni sikerim"))