package com.example.gestaoestoque.entities;

import com.example.gestaoestoque.entities.enums.ProductCategoryType;
import com.example.gestaoestoque.entities.enums.ProductStatusType;
import com.example.gestaoestoque.exceptions.ResourceNotValidException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Version
    private int version;
    @ManyToOne
    private ProductCategory category;
    private BigDecimal price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private ProductStatusType status;

    public void output(int quantity) {
        if (this.stock < quantity) {
            throw new ResourceNotValidException("Insufficient stock");
        }
        this.stock -= quantity;
    }

    public void input(int quantity) {
        this.stock += quantity;
    }
}
