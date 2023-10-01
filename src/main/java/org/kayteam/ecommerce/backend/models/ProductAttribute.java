package org.kayteam.ecommerce.backend.models;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "product_attributes")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    public String attributeCategoryName;

    @ElementCollection
    @CollectionTable(name = "product_attributes_mapping",
            joinColumns = {@JoinColumn(name = "product_attribute_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "attribute_name")
    public Map<String, String> attributesMap;
}
