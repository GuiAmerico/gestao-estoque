package com.example.gestaoestoque.controllers;

import com.example.gestaoestoque.controllers.request.CreateProductRequest;
import com.example.gestaoestoque.controllers.request.UpdateProductRequest;
import com.example.gestaoestoque.controllers.response.ProductResponse;
import com.example.gestaoestoque.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest product) {
    ProductResponse newProduct = productService.createProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
  }

  @GetMapping("/low-stock")
  public ResponseEntity<Page<ProductResponse>> getLowStockProducts(
    @PageableDefault(size = 15) Pageable pageable
  ) {
    Page<ProductResponse> products = productService.findLowStockProduct(pageable);
    return ResponseEntity.ok(products);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(
    @RequestBody @Valid UpdateProductRequest product,
    @PathVariable Long id
  ) {
    productService.updateProduct(id,product);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponse>> findAllProducts(
    @PageableDefault(size = 15) Pageable pageable
  ) {
    Page<ProductResponse> products = productService.findAllProducts(pageable);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> findProductById(@PathVariable Long id) {
    ProductResponse product = productService.findProductById(id);
    return ResponseEntity.ok(product);
  }
}
