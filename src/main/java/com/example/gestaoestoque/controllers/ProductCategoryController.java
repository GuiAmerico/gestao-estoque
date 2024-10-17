package com.example.gestaoestoque.controllers;

import com.example.gestaoestoque.controllers.request.MovementRequest;
import com.example.gestaoestoque.controllers.request.ProductCategoryRequest;
import com.example.gestaoestoque.controllers.response.MovementResponse;
import com.example.gestaoestoque.controllers.response.ProductCategoryResponse;
import com.example.gestaoestoque.services.MovementService;
import com.example.gestaoestoque.services.ProductCategoryService;
import com.example.gestaoestoque.services.ProductService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

  private final ProductCategoryService productCategoryService;

  @PostMapping
  public ResponseEntity<ProductCategoryResponse> createProductCategory(@RequestBody @Valid ProductCategoryRequest category) {
    ProductCategoryResponse newCategory = productCategoryService.createProductCategory(category);
    return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
  }

  @GetMapping
  public ResponseEntity<List<ProductCategoryResponse>> findAllProductCategories() {
    List<ProductCategoryResponse> categories = productCategoryService.findAllProductCategories();
    return ResponseEntity.ok(categories);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateProductCategory(
    @RequestBody @Valid ProductCategoryRequest category,
    @PathVariable Long id
  ) {
    productCategoryService.updateProductCategory(id,category);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
    productCategoryService.deleteProductCategory(id);
    return ResponseEntity.noContent().build();
  }


}
