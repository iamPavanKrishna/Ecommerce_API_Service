from flask import Flask, request, render_template
from flask_cors import CORS, cross_origin
import requests
import subprocess
from services.ThirdPartyProductService import ThirdPartyProductService
from services.SelfProductService import SelfProductService

app = Flask(__name__)
cors = CORS(app)

app.config['CORS_HEADERS'] = 'content_type'

@app.route('/', methods=['GET', 'POST'])
def home():

    return 'render main index.html template here'
    #return render_template("index.html")


"""
SELF PRODUCT SERVICE ROUTES
"""
@app.route('/self/getallproducts', methods=['GET'])
def getallproducts():

    return SelfProductService().getallproducts()


@app.route('/self/getproduct', methods=['GET', 'POST'])
def getproduct():
    id =  request.args.get('id')

    return SelfProductService().getproductbyid(id)


@app.route('/self/createproduct', methods=['GET', 'POST'])
def createproduct():
    title = request.args.get('title')
    price = request.args.get('price')
    url = request.args.get('url')
    currency = request.args.get('currency')
    description = request.args.get('description')

    return SelfProductService().createproduct(title, price, url, currency, description)


@app.route('/self/updateproduct', methods=['GET', 'POST'])
def updateproduct():
    id = request.args.get('id')
    title = request.args.get('title')
    price = request.args.get('price')
    url = request.args.get('url')
    currency = request.args.get('currency')
    description = request.args.get('description')

    return SelfProductService().updateproductbyid(id, title, price, url, currency, description)


@app.route('/self/deleteproduct', methods=['GET', 'POST'])
def deleteproduct():
    id = request.args.get('id')

    return SelfProductService().deleteproductbyid(id)


"""
THIRDPARTY SERVICE ROUTES
"""
@app.route('/thirdparty/getallproducts', methods=['GET'])
def tpgetallproducts():

    return ThirdPartyProductService().getallproducts()


@app.route('/thirdparty/getproduct', methods=['GET', 'POST'])
def tpgetproduct():
    id =  request.args.get('id')

    return ThirdPartyProductService().getproductbyid(id)


@app.route('/thirdparty/createproduct', methods=['GET', 'POST'])
def tpcreateproduct():
    id = request.args.get('id')
    title = request.args.get('title')
    price = request.args.get('price')
    category = request.args.get('category')
    description = request.args.get('description')
    image = request.args.get('image')

    return ThirdPartyProductService().createproduct(id, title, price, category, description, image)


@app.route('/thirdparty/updateproduct', methods=['GET', 'POST'])
def tpupdateproduct():
    id = request.args.get(id)

    return ThirdPartyProductService().updateproductbyid(id)


@app.route('/thirdparty/deleteproduct', methods=['GET', 'POST'])
def tpdeleteproduct():
    id = request.args.get('id')

    return ThirdPartyProductService().deleteproductbyid(id)


@app.route('/thirdparty/getcategories', methods=['GET'])
def tpgetcategories():

    return ThirdPartyProductService().getallcategories()


@app.route('/thirdparty/productbycategory', methods=['GET', 'POST'])
def tpproductbycategory():
    category = request.args.get('category')

    return ThirdPartyProductService().getproductbycategory(category)


if __name__ == "__main__":
    app.run(debug=True)
