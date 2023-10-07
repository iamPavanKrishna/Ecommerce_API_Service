package dev.pavan.ecommerce_product_api_service.repos;

import dev.pavan.ecommerce_product_api_service.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value = "select * from products p where p.category_id = ?1", nativeQuery = true)
    List<Product> findByCategory(UUID categoryId);
}
