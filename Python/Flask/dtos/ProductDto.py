from dataclasses import dataclass

@dataclass
class ProductDto:
    id : str
    title : str
    price : float
    url : str
    currency: str
    description : str
