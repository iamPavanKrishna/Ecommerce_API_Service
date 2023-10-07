package dev.pavan.ecommerce_product_api_service.controllers;

import dev.pavan.ecommerce_product_api_service.dtos.ProductDto;
import dev.pavan.ecommerce_product_api_service.exceptions.NotFoundException;
import dev.pavan.ecommerce_product_api_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "sort", required = false) String sort
    ) throws NotFoundException {
        return ResponseEntity
                .ok(productService.getAllProducts(category, limit, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") String id) throws NotFoundException {
        return ResponseEntity
                .ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(requestDto));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(productService.getAllCategories());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable("id") String id,
            @RequestBody ProductDto requestDto
    ) {
        return ResponseEntity
                .ok(productService.updateProduct(requestDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") String id) throws NotFoundException {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(productService.deleteProduct(id));
    }

}
