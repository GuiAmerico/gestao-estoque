package com.example.gestaoestoque.services;

import com.example.gestaoestoque.controllers.request.ProductCategoryRequest;
import com.example.gestaoestoque.controllers.response.ProductCategoryResponse;
import java.util.List;

public interface ProductCategoryService {

    ProductCategoryResponse createProductCategory(ProductCategoryRequest category);

    List<ProductCategoryResponse> findAllProductCategories();

    void updateProductCategory(Long id, ProductCategoryRequest category);

    void deleteProductCategory(Long id);
}
