from dataclasses import dataclass

@dataclass
class ProductDto:
    id : str
    title : str
    price : float
    category : str
    description : str
    image : str
