import zope.interface
from services import ProductService
import json
import requests

@zope.interface.implementer(ProductService)
class ThirdPartyProductService():
    def getallproducts():
        url = 'https://fakestoreapi.com/products'
        resp = requests(url)

        return resp.json()


    def getproductbyid(id):
        #TODO validations
        url = f'https://fakestoreapi.com/products/{id}'
        resp = requests(url)

        return resp.json()
    

    def createproduct(id, title, price, category, description, image):
        url = 'https://fakestoreapi.com/products'
        data = {
                    'title': title,
                    'price': price,
                    'description': description,
                    'image': image,
                    'category': category
                }
        data = json.loads(json.dumps(data))

        resp = requests(url, json={
            "method":"POST",
            "body": data
        })

        return resp.json()
    

    def updateproductbyid(id, title, price, category, description, image):
        url = f'https://fakestoreapi.com/products/{id}'
        data = {
                    'title': title,
                    'price': price,
                    'description': description,
                    'image': image,
                    'category': category
                }
        data = json.loads(json.dumps(data))

        resp = requests(url, json={
            "method":"PUT",
            "body": data
        })

        return resp.json()
    

    def deleteproductbyid(id):
        url = f'https://fakestoreapi.com/products/{id}'

        resp = requests(url, json={
            "method":"DELETE",
        })

        return resp.json()
    

    def getallcategories():
        url = 'https://fakestoreapi.com/products/categories'
        resp = requests(url)

        return resp.json()
    

    def getproductbycategory(category):
        url = f'https://fakestoreapi.com/products/category/{category}'
        resp = requests(url)

        return resp.json()
