package com.example.gestaoestoque.services;

import com.example.gestaoestoque.controllers.request.CreateProductRequest;
import com.example.gestaoestoque.controllers.request.UpdateProductRequest;
import com.example.gestaoestoque.controllers.response.BestSellingProductResponse;
import com.example.gestaoestoque.controllers.response.ProductResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest productRequest);
    Page<ProductResponse> findLowStockProduct(Pageable pageable);

    List<BestSellingProductResponse> findBestSellingProduct();

    void updateProduct(Long id, UpdateProductRequest productRequest);
    void deleteProduct(Long id);
    Page<ProductResponse> findAllProducts(Pageable pageable);
    ProductResponse findProductById(Long id);
}
