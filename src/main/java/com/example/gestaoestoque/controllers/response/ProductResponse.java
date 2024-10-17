package com.example.gestaoestoque.controllers.response;


import com.example.gestaoestoque.entities.ProductCategory;
import com.example.gestaoestoque.entities.enums.ProductCategoryType;
import com.example.gestaoestoque.entities.enums.ProductStatusType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponse {

  private Long id;
  private String name;
  private ProductCategory category;
  private BigDecimal price;
  private int stock;
  private ProductStatusType status;
}
