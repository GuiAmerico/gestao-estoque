package com.example.gestaoestoque.services.imp;

import com.example.gestaoestoque.controllers.request.CreateProductRequest;
import com.example.gestaoestoque.controllers.request.UpdateProductRequest;
import com.example.gestaoestoque.controllers.response.BestSellingProductResponse;
import com.example.gestaoestoque.controllers.response.ProductResponse;
import com.example.gestaoestoque.entities.Product;
import com.example.gestaoestoque.entities.ProductCategory;
import com.example.gestaoestoque.exceptions.ResourceAlreadyExistsException;
import com.example.gestaoestoque.exceptions.ResourceNotFoundException;
import com.example.gestaoestoque.repositories.ProductCategoryRepository;
import com.example.gestaoestoque.repositories.ProductRepository;
import com.example.gestaoestoque.services.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImp implements ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;
  private final ModelMapper mapper;
  @Override
  public ProductResponse createProduct(CreateProductRequest productRequest) {
    boolean productAlreadyExists = productRepository.existsByName(productRequest.getName());
    if (productAlreadyExists) {
      throw new ResourceAlreadyExistsException("Product already exists");
    }
    ProductCategory category = productCategoryRepository
      .findByName(productRequest.getCategoryName())
      .orElseThrow(() -> new ResourceAlreadyExistsException("Category not found"));

    Product product = mapper.map(productRequest, Product.class);
    product.setCategory(category);

    return mapper.map(productRepository.save(product), ProductResponse.class);
  }

  @Override
  public Page<ProductResponse> findLowStockProduct(Pageable pageable) {

    return productRepository.findByStockLessThanEqual(10, pageable)
      .map(product -> mapper.map(product, ProductResponse.class));
  }

  @Override
  public List<BestSellingProductResponse> findBestSellingProduct() {
    return productRepository.findBestSellingProducts()
      .stream()
      .map(BestSellingProductResponse::new)
      .toList();
  }

  @Override
  public void updateProduct(Long id, UpdateProductRequest productRequest) {
    boolean productAlreadyExists = productRepository.existsByName(productRequest.getName());
    if (productAlreadyExists) {
      throw new ResourceAlreadyExistsException("Product already exists");
    }
    Product product = productRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    mapper.map(productRequest, product);
    if (productRequest.getCategoryName() != null){
      ProductCategory category = productCategoryRepository
        .findByName(productRequest.getCategoryName())
        .orElseThrow(() -> new ResourceAlreadyExistsException("Category not found"));

      product.setCategory(category);
    }
    productRepository.save(product);
  }

  @Override
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

  @Override
  public Page<ProductResponse> findAllProducts(Pageable pageable) {
    return productRepository
      .findAll(pageable)
      .map(product -> mapper.map(product, ProductResponse.class));
  }

  @Override
  public ProductResponse findProductById(Long id) {
    return productRepository
      .findById(id)
      .map(product -> mapper.map(product, ProductResponse.class))
      .orElseThrow(() -> new ResourceAlreadyExistsException("Product not found"));
  }
}
