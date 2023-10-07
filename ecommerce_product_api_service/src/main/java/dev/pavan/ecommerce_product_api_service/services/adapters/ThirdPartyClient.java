package dev.pavan.ecommerce_product_api_service.services.adapters;

import dev.pavan.ecommerce_product_api_service.dtos.ProductDto;
import dev.pavan.ecommerce_product_api_service.exceptions.NotFoundException;

import java.util.List;

public interface ThirdPartyClient {
    ProductDto getProductById(String id) throws NotFoundException;

    List<ProductDto> getAllProducts(String category, Integer limit, String sort);

    ProductDto createProduct(ProductDto productDto);

    List<String> getAllCategories();

    ProductDto updateProduct(ProductDto productDto, String id);

    ProductDto deleteProduct(String id);
}
