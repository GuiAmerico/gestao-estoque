package com.example.gestaoestoque.repositories;

import com.example.gestaoestoque.entities.Product;
import com.example.gestaoestoque.entities.ProductCategory;
import com.example.gestaoestoque.repositories.projections.BestSellingProductProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByName(String name);

    boolean existsByName(String name);
}
