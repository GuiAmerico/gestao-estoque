package com.example.gestaoestoque.repositories.projections;

import com.example.gestaoestoque.entities.Product;

public interface BestSellingProductProjection {

  Product getProduct();
  int getTotalSold();
}
