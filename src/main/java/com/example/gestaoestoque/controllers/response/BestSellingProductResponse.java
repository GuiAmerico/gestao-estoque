package com.example.gestaoestoque.controllers.response;


import com.example.gestaoestoque.entities.Product;
import com.example.gestaoestoque.entities.enums.ProductCategoryType;
import com.example.gestaoestoque.entities.enums.ProductStatusType;
import com.example.gestaoestoque.repositories.projections.BestSellingProductProjection;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BestSellingProductResponse {

  private ProductResponse product;
  private int totalSold;

  public BestSellingProductResponse(BestSellingProductProjection projection) {
    Product productProjection = projection.getProduct();
    this.product = new ProductResponse(
      productProjection.getId(),
      productProjection.getName(),
      productProjection.getCategory(),
      productProjection.getPrice(),
      productProjection.getStock(),
      productProjection.getStatus()
    );
    this.totalSold = projection.getTotalSold();
  }
}
