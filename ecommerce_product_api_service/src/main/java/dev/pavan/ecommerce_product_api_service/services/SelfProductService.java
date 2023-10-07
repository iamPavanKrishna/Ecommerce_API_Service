package dev.pavan.ecommerce_product_api_service.services;

import dev.pavan.ecommerce_product_api_service.dtos.ProductDto;
import dev.pavan.ecommerce_product_api_service.exceptions.NotFoundException;
import dev.pavan.ecommerce_product_api_service.models.Category;
import dev.pavan.ecommerce_product_api_service.models.Price;
import dev.pavan.ecommerce_product_api_service.models.Product;
import dev.pavan.ecommerce_product_api_service.repos.CategoryRepository;
import dev.pavan.ecommerce_product_api_service.repos.PriceRepository;
import dev.pavan.ecommerce_product_api_service.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Component("selfProductService")
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(
            ProductRepository productRepository,
            PriceRepository priceRepository,
            CategoryRepository categoryRepository
    ) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto getProductById(String id) throws NotFoundException {
        UUID uuid = UUID.fromString(id);
        Product product  = productRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Product with id: " + id + " not found"));

        return ProductDto.builder()
                .id(product.getId().toString())
                .title(product.getTitle())
                .category(product.getCategory().getName())
                .currency(product.getCost().getCurrency())
                .price(product.getCost().getAmount())
                .description(product.getDescription())
                .build();
    }

    // todo: based on category with limit and order
    @Override
    public List<ProductDto> getAllProducts(String category, Integer limit, String sort) throws NotFoundException {
        List<Product> products;
        if(category != null) {
            Category storedCategory = categoryRepository.findByName(category)
                    .orElseThrow(() -> new NotFoundException("Category with name '" + category + "' is not found."));
            products = productRepository.findByCategory(storedCategory.getId());
        }
        else products = productRepository.findAll();

        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product: products) {
            productDtoList.add(
                    ProductDto.builder()
                            .id(product.getId().toString())
                            .title(product.getTitle())
                            .category(product.getCategory().getName())
                            .currency(product.getCost().getCurrency())
                            .price(product.getCost().getAmount())
                            .description(product.getDescription())
                            .build()
            );
        }

        return productDtoList;
    }

    @Override
    public ProductDto createProduct(ProductDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setDescription(requestDto.getDescription());

        Price price = new Price(requestDto.getCurrency(), requestDto.getPrice());
        Price savedPrice = priceRepository.save(price);
        product.setCost(savedPrice);

        Category savedCategory = getCategory(requestDto.getCategory());
        product.setCategory(savedCategory);

        Product savedProduct = productRepository.save(product);
        return ProductDto.builder()
                .id(savedProduct.getId().toString())
                .category(savedProduct.getCategory().getName())
                .title(savedProduct.getTitle())
                .description(savedProduct.getDescription())
                .currency(savedProduct.getCost().getCurrency())
                .price(savedProduct.getCost().getAmount())
                .build();
    }

    private Category getCategory(String category) {
        Optional<Category> storedCategory = categoryRepository.findByName(category);
        if(storedCategory.isEmpty()) {
            Category newCategory = new Category(category);
            return categoryRepository.save(newCategory);
        }
        return storedCategory.get();

    }

    @Override
    public List<String> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(Category::getName)
                .toList();
    }

    @Override
    public ProductDto updateProduct(ProductDto requestDto, String id) {
        UUID uuid = UUID.fromString(id);
        Product product = productRepository.getReferenceById(uuid);
        product.setTitle(requestDto.getTitle());
        product.setDescription(requestDto.getDescription());

        Price price = product.getCost();
        price.setCurrency(requestDto.getCurrency());
        price.setAmount(requestDto.getPrice());
        Price savedPrice = priceRepository.save(price);
        product.setCost(savedPrice);

        Category category = product.getCategory();
        category.setName(category.getName());
        Category savedCategory = categoryRepository.save(category);
        product.setCategory(savedCategory);

        Product savedProduct = productRepository.save(product);
        return ProductDto.builder()
                .id(savedProduct.getId().toString())
                .category(savedProduct.getCategory().getName())
                .title(savedProduct.getTitle())
                .description(savedProduct.getDescription())
                .currency(savedProduct.getCost().getCurrency())
                .price(savedProduct.getCost().getAmount())
                .build();
    }

    @Override
    public ProductDto deleteProduct(String id) throws NotFoundException {
        UUID uuid = UUID.fromString(id);
        productRepository.deleteById(uuid);
        Product product  = productRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Product with id: " + id + " not found"));
        productRepository.delete(product);
        return ProductDto.builder().build();
    }
}
