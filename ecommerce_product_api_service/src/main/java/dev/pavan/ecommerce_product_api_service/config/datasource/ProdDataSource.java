package dev.pavan.ecommerce_product_api_service.config.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProdDataSource implements DataSourceConfig {

    @Value("${message}")
    String profile;

    @Override
    public void setup() {
        System.out.printf("Setting up: " + profile);
    }
}
