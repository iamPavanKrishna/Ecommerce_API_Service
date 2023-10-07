package dev.pavan.ecommerce_product_api_service.repos;

import dev.pavan.ecommerce_product_api_service.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Override
    <S extends Price> S save(S entity);
}
