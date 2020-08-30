import re
import string
import numpy as np

from nltk.corpus import stopwords
# from nltk.stem import PorterStemmer
from snowballstemmer import TurkishStemmer
from nltk.tokenize import TweetTokenizer



def process_tweet(tweet):
    """Process tweet function.
    Input:
        tweet: a string containing a tweet
    Output:
        tweets_clean: a list of words containing the processed tweet
    """
    stemmer = TurkishStemmer()
    stopwords_english = stopwords.words('turkish')
    # remove stock market tickers like $GE
    tweet = re.sub(r'\$\w*', '', tweet)
    # remove old style retweet text "RT"
    tweet = re.sub(r'^RT[\s]+', '', tweet)
    # remove hyperlinks
    tweet = re.sub(r'https?:\/\/.*[\r\n]*', '', tweet)
    # remove hashtags
    # only removing the hash # sign from the word
    tweet = re.sub(r'#', '', tweet)
    # tokenize tweets
    tokenizer = TweetTokenizer(preserve_case=False, strip_handles=True,
                               reduce_len=True)
    tweet_tokens = tokenizer.tokenize(tweet)

    tweets_clean = []
    for word in tweet_tokens:
        if (word not in stopwords_english and  # remove stopwords
                word not in string.punctuation):  # remove punctuation
            # tweets_clean.append(word)
            stem_word = stemmer.stemWord(word)  # stemming word
            tweets_clean.append(stem_word)

    return ' '.join([elem for elem in tweets_clean])
if __name__ == '__main__':
    print(process_tweet("Selamlar herkese, ben ekrem bal."))
