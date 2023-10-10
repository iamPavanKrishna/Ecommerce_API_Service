from flask import Flask, request, render_template
from flask_cors import CORS, cross_origin
import requests
import subprocess
from services.ThirdPartyProductService import ThirdPartyProductService

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

    return 'attach get all products service here'


@app.route('/self/getproduct', methods=['GET', 'POST'])
def getproduct():
    id =  request.args.get('id')

    return f'attach get product by id service here {id}'


@app.route('/self/createproduct', methods=['GET', 'POST'])
def createproduct():
    id = request.args.get('id')
    title = request.args.get('title')
    price = request.args.get('price')
    category = request.args.get('category')
    description = request.args.get('description')
    image = request.args.get('image')

    return f'attach create product service here, {id}, {title}, {price}, {category}, {description}, {image}'


@app.route('/self/updateproduct', methods=['GET', 'POST'])
def updateproduct():
    id = request.args.get(id)

    return 'attach update product by id service here'


@app.route('/self/deleteproduct', methods=['GET', 'POST'])
def deleteproduct():
    id = request.args.get('id')

    return 'attach delete product by id service here'


@app.route('/self/getcategories', methods=['GET'])
def getcategories():

    return 'attach get categories service here'

@app.route('/self/productbycategory', methods=['GET', 'POST'])
def productbycategory():
    category = request.args.get('category')

    return 'attach product by category service here'


"""
THIRDPARTY SERVICE ROUTES
"""
@app.route('/thirdparty/getallproducts', methods=['GET'])
def getallproducts():

    return ThirdPartyProductService().getallproducts()


@app.route('/thirdparty/getproduct', methods=['GET', 'POST'])
def getproduct():
    id =  request.args.get('id')

    return ThirdPartyProductService().getproductbyid(id)


@app.route('/thirdparty/createproduct', methods=['GET', 'POST'])
def createproduct():
    id = request.args.get('id')
    title = request.args.get('title')
    price = request.args.get('price')
    category = request.args.get('category')
    description = request.args.get('description')
    image = request.args.get('image')

    return ThirdPartyProductService().createproduct(id, title, price, category, description, image)


@app.route('/thirdparty/updateproduct', methods=['GET', 'POST'])
def updateproduct():
    id = request.args.get(id)

    return ThirdPartyProductService().updateproductbyid(id)


@app.route('/thirdparty/deleteproduct', methods=['GET', 'POST'])
def deleteproduct():
    id = request.args.get('id')

    return ThirdPartyProductService().deleteproductbyid(id)


@app.route('/thirdparty/getcategories', methods=['GET'])
def getcategories():

    return ThirdPartyProductService().getallcategories()


@app.route('/thirdparty/productbycategory', methods=['GET', 'POST'])
def productbycategory():
    category = request.args.get('category')

    return ThirdPartyProductService().getproductbycategory(category)


if __name__ == "__main__":
    app.run(debug=True)
