package com.example.gestaoestoque.services.imp;

import com.example.gestaoestoque.controllers.request.ProductCategoryRequest;
import com.example.gestaoestoque.controllers.response.ProductCategoryResponse;
import com.example.gestaoestoque.entities.ProductCategory;
import com.example.gestaoestoque.exceptions.ResourceAlreadyExistsException;
import com.example.gestaoestoque.exceptions.ResourceNotFoundException;
import com.example.gestaoestoque.repositories.ProductCategoryRepository;
import com.example.gestaoestoque.services.ProductCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductCategoryServiceImp implements ProductCategoryService {

  private final ProductCategoryRepository productCategoryRepository;
  private final ModelMapper mapper;

  @Override
  public ProductCategoryResponse createProductCategory(ProductCategoryRequest category) {
    boolean exists = productCategoryRepository.existsByName(category.getName());
    if (exists) {
      throw new ResourceAlreadyExistsException("Category already exists");
    }

    ProductCategory productCategory = mapper.map(category, ProductCategory.class);

    return mapper.map(
      productCategoryRepository.save(productCategory),
      ProductCategoryResponse.class
    );
  }

  @Override
  public List<ProductCategoryResponse> findAllProductCategories() {
    return productCategoryRepository
      .findAll()
      .stream()
      .map(category -> mapper.map(category, ProductCategoryResponse.class))
      .toList();
  }

  @Override
  public void updateProductCategory(Long id, ProductCategoryRequest category) {
    boolean exists = productCategoryRepository.existsByName(category.getName());
    if (exists) {
      throw new ResourceAlreadyExistsException("Category already exists");
    }

    ProductCategory productCategory = productCategoryRepository
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

    mapper.map(category, productCategory);

    productCategoryRepository.save(productCategory);
  }

  @Override
  public void deleteProductCategory(Long id) {
    productCategoryRepository.deleteById(id);
  }
}
