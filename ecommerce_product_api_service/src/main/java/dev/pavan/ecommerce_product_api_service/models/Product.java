package dev.pavan.ecommerce_product_api_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "products")
public class Product extends BaseModel {
    private String title;
    private String description;

    @OneToOne
    private Price cost;

    @ManyToOne
    private Category category;
}
