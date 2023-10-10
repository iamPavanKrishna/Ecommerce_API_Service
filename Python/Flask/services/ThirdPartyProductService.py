import zope.interface
from services.ProductService import ProductService
import json
import requests

@zope.interface.implementer(ProductService)
class ThirdPartyProductService():
    def getallproducts(self):
        url = 'https://fakestoreapi.com/products'
        resp = requests.get(url)

        return resp.json()


    def getproductbyid(self, id):
        #TODO validations
        url = f'https://fakestoreapi.com/products/{id}'
        resp = requests.get(url)

        return resp.json()
    

    def createproduct(self, id, title, price, category, description, image):
        url = 'https://fakestoreapi.com/products'
        data = {
                    'id' : id,
                    'title': title,
                    'price': price,
                    'description': description,
                    'image': image,
                    'category': category
                }
        data = json.loads(json.dumps(data))

        resp = requests.get(url, json={
            "method":"POST",
            "body": data
        })

        return resp.json()
    

    def updateproductbyid(self, id, title, price, category, description, image):
        url = f'https://fakestoreapi.com/products/{id}'
        data = {
                    'title': title,
                    'price': price,
                    'description': description,
                    'image': image,
                    'category': category
                }
        data = json.loads(json.dumps(data))

        resp = requests.get(url, json={
            "method":"PUT",
            "body": data
        })

        return resp.json()
    

    def deleteproductbyid(self, id):
        url = f'https://fakestoreapi.com/products/{id}'

        resp = requests.get(url, json={
            "method":"DELETE",
        })

        return resp.json()
    

    def getallcategories(self):
        url = 'https://fakestoreapi.com/products/categories'
        resp = requests.get(url)

        return resp.json()
    

    def getproductbycategory(self, category):
        url = f'https://fakestoreapi.com/products/category/{category}'
        resp = requests.get(url)

        return resp.json()
