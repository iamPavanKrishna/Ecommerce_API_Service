package dev.pavan.ecommerce_product_api_service.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class BaseModelTest {
    @Test
    void testId() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
    }
}
