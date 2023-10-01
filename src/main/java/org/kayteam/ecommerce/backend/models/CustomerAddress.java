package org.kayteam.ecommerce.backend.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "customer_addresses")
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    public String street;

    @Column(nullable = false)
    public String number;

    @Nullable
    public String apartment;

    @Nullable
    public String description;

    @Column(nullable = false)
    public String phoneNumber;

    @Column(nullable = false)
    public String fullName;

    @Column(nullable = false)
    public String country;

    @Column(nullable = false)
    public String postalCode;

    @Column(nullable = false)
    public String city;

    @ManyToOne
    @Column(nullable = false)
    public User user;
}
