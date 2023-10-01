package org.kayteam.ecommerce.backend.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "product_shipping_info")
public class ProductShippingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Nullable
    public double declaredValue;

    @Column(nullable = false)
    public double cubicCentimetersVolume;

    @Nullable
    public double weightInKilograms;

    @Nullable
    public double height;

    @Nullable
    public double width;

    @Nullable
    public double length;

    @OneToOne
    public Product product;
}
