package com.example.gestaoestoque.controllers.request;

import com.example.gestaoestoque.entities.enums.ProductStatusType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateProductRequest {

  private String name;
  private String categoryName;
  private BigDecimal price;
  private int stock;
  private ProductStatusType status;
}
