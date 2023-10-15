import zope.interface
from services.ProductService import ProductService
import sqlite3
import json
import requests

@zope.interface.implementer(ProductService)
class SelfProductService():
    def getallproducts(self):
        conn = sqlite3.connect('./Python/Flask/demoproducts.db')
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()

        fetch_all_products_query = '''
                                      SELECT id, title, url, price, currency, description FROM products;
                                   '''

        res = cur.execute(fetch_all_products_query).fetchall()
        data = json.dumps([dict(i) for i in res])

        return data


    def getproductbyid(self, id):
        #TODO validations
        conn = sqlite3.connect('./Python/Flask/demoproducts.db')
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()

        fetch_product_by_id_query = f'''
                                      SELECT title, url, price, currency, description
                                      FROM products 
                                      WHERE id={id};
                                   '''

        res = cur.execute(fetch_product_by_id_query).fetchone()
        data = json.dumps(dict(res))

        return data
    

    def createproduct(self, title, price, url, currency, description):
        conn = sqlite3.connect('./Python/Flask/demoproducts.db')
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()

        create_product_query = '''
                                      INSERT INTO products(title, url, price, currency, description)
                                      VALUES(?, ?, ?, ?, ?);
                                   '''
        
        contents = (title, price, url, currency, description)

        cur.execute(create_product_query, contents)
        conn.commit()
        
        id = cur.execute(f"SELECT id FROM products ORDER BY id DESC LIMIT 1;").fetchone()

        conn.close()

        return f"Successfully Created the product with id {id[0]}"
    

    def updateproductbyid(self, id, title, price, url, currency, description):
        conn = sqlite3.connect('./Python/Flask/demoproducts.db')
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()

        update_product_query = f'''
                                      UPDATE products SET
                                      title = '{title}',
                                      price = '{price}',
                                      url = {url},
                                      currency = {currency},
                                      description = {description}
                                      WHERE id = {id};
                                   '''

        res = cur.execute(update_product_query)
        conn.commit()
        conn.close()

        return f"Successfully updated the data of Product with id = {id}"
    

    def deleteproductbyid(self, id):
        conn = sqlite3.connect('./Python/Flask/demoproducts.db')
        conn.row_factory = sqlite3.Row
        cur = conn.cursor()

        fetch_all_products_query = f'''
                                      DELETE FROM products WHERE id ={id};
                                   '''

        cur.execute(fetch_all_products_query)
        conn.commit()
        conn.close()

        return f"Product Successfully deleted with id = {id}"

