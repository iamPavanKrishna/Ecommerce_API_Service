package dev.pavan.ecommerce_product_api_service.services.adapters;

import dev.pavan.ecommerce_product_api_service.dtos.FakeStoreProductDto;
import dev.pavan.ecommerce_product_api_service.dtos.ProductDto;
import dev.pavan.ecommerce_product_api_service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Primary
@Component("fakeStoreProductClient")
public class FakeStoreProductClient implements ThirdPartyClient {
    private final RestTemplateBuilder restTemplateBuilder;
    private final String productRequestUrl;

    @Autowired
    public FakeStoreProductClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${fakeStore.api.url}") String fakeStoreApiUrl,
            @Value("${fakeStore.api.path.products}") String fakeStoreProductPath
    ) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.productRequestUrl = fakeStoreApiUrl + fakeStoreProductPath;
    }

    @Override
    public ProductDto getProductById(String id) throws NotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String requestUrl = productRequestUrl + "/{id}";

        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(requestUrl, FakeStoreProductDto.class, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if(fakeStoreProductDto == null)
            throw new NotFoundException("Product with id: " + id + " not found");
        return convertToProductDto(fakeStoreProductDto);
    }

    @Override
    public List<ProductDto> getAllProducts(String category, Integer limit, String sort) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // non-modular
        String categoryUrl = category == null ? productRequestUrl : productRequestUrl + "/category/" + category;

        Map<String, Object> params = new HashMap<>();
        params.put("limit", limit);
        params.put("sort", sort);

        String requestUrl = setQueryParam(categoryUrl, params);
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(requestUrl, FakeStoreProductDto[].class);

        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(this::convertToProductDto)
                .toList();
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(productRequestUrl, productDto, FakeStoreProductDto.class);
        return convertToProductDto(response.getBody());
    }

    @Override
    public List<String> getAllCategories() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String requestUrl = productRequestUrl + "/categories";

        ResponseEntity<String[]> response = restTemplate.getForEntity(requestUrl, String[].class);
        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .toList();
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String requestUrl = productRequestUrl + "/{id}";

        HttpEntity<ProductDto> httpEntity = new HttpEntity<>(productDto);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(requestUrl, HttpMethod.PUT, httpEntity, FakeStoreProductDto.class, id);
        return convertToProductDto(response.getBody());
    }

    @Override
    public ProductDto deleteProduct(String id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String requestUrl = productRequestUrl + "/{id}";

        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(requestUrl, HttpMethod.DELETE, HttpEntity.EMPTY, FakeStoreProductDto.class, id);
        return convertToProductDto(response.getBody());
    }

    private String setQueryParam(String requestUrl, Map<String, Object> params) {
        StringBuilder queryString = new StringBuilder();
        for(Map.Entry<String, Object> param: params.entrySet()) {
            String key = param.getKey();
            Object value = param.getValue();
            if(value != null) {
                // ?param=1&param=2
                queryString.append(queryString.isEmpty() ? "?" : "&")
                        .append(key).append("=").append(value);
            }
        }

        return requestUrl + queryString;
    }

    private ProductDto convertToProductDto(FakeStoreProductDto fakeStoreProductDto) {
        return ProductDto.builder()
                .id(Objects.requireNonNull(fakeStoreProductDto).getId().toString())
                .title(fakeStoreProductDto.getTitle())
                .price(fakeStoreProductDto.getPrice())
                .description(fakeStoreProductDto.getDescription())
                .category(fakeStoreProductDto.getCategory())
                .image(fakeStoreProductDto.getImage())
                .build();
    }
}
