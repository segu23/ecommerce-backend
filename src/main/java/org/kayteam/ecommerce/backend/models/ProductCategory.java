package org.kayteam.ecommerce.backend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    public ProductCategory parentCategory;

    public String categoryName;

    public String categoryDescription;

    public String icon;

    public String image;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    @Getter
    private Date updateDate;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @Getter
    private Date registrationDate;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    private User updatedBy;
}
