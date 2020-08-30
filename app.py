import time
from flask import Flask, request, jsonify
import predict

app = Flask(__name__)

@app.route('/nlp/<string:string>', methods=['GET'])
def nlp(string):
    if predict.is_this_hate_message(string):
        return "1"
    else:
        return "0"
        # return jsonify(error='Who dis?', user=user), 404

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)