from dataclasses import dataclass

@dataclass
class FakeStoreProductDto:
    id : int
    title : str
    price : float
    category : str
    description : str
    image : str
