package com.example.gestaoestoque.repositories;

import com.example.gestaoestoque.entities.Product;
import com.example.gestaoestoque.repositories.projections.BestSellingProductProjection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByStockLessThanEqual(int stock, Pageable pageable);


    @Query("SELECT m.product as product, SUM(m.quantity) as totalSold FROM Movement m WHERE m.type = com.example.gestaoestoque.entities.enums.MovementType.OUTPUT GROUP BY m.product ORDER BY totalSold DESC")
    List<BestSellingProductProjection> findBestSellingProducts();

    boolean existsByName(String name);
}
