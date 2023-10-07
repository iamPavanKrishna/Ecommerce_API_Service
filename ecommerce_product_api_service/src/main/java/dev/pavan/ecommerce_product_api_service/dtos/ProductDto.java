package dev.pavan.ecommerce_product_api_service.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {
    private String id;
    private String title;
    private String description;
    private String category;
    private String currency;
    private double price;
    private String image;
}
