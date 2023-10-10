import zope.interface

class ProductService(zope.interface.Interface):
    zope.interface.Attribute('foo')
    def getallproducts():
        pass
    def getproductbyid(id):
        pass
    def createproduct(id, title, price, category, description, image):
        pass
    def updateproductbyid(id):
        pass
    def deleteproductbyid(id):
        pass
    def getallcategories():
        pass
    def getproductbycategory(category):
        pass
