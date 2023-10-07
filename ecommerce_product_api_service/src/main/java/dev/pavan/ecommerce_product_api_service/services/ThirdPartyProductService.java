package dev.pavan.ecommerce_product_api_service.services;

import dev.pavan.ecommerce_product_api_service.dtos.ProductDto;
import dev.pavan.ecommerce_product_api_service.exceptions.NotFoundException;
import dev.pavan.ecommerce_product_api_service.services.adapters.ThirdPartyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("thirdPartyProductService")
public class ThirdPartyProductService implements ProductService {

    private final ThirdPartyClient thirdPartyClient;

    @Autowired
    public ThirdPartyProductService(ThirdPartyClient thirdPartyClient) {
        this.thirdPartyClient = thirdPartyClient;
    }

    @Override
    public ProductDto getProductById(String id) throws NotFoundException {
        return thirdPartyClient.getProductById(id);
    }

    @Override
    public List<ProductDto> getAllProducts(String category, Integer limit, String sort) {
        return thirdPartyClient.getAllProducts(category, limit, sort);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return thirdPartyClient.createProduct(productDto);
    }

    @Override
    public List<String> getAllCategories() {
        return thirdPartyClient.getAllCategories();
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String id) {
        return thirdPartyClient.updateProduct(productDto, id);
    }

    @Override
    public ProductDto deleteProduct(String id) {
        return thirdPartyClient.deleteProduct(id);
    }
}
