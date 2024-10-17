package com.example.gestaoestoque.services.imp;

import com.example.gestaoestoque.controllers.request.MovementRequest;
import com.example.gestaoestoque.controllers.response.MovementResponse;
import com.example.gestaoestoque.entities.Movement;
import com.example.gestaoestoque.entities.Product;
import com.example.gestaoestoque.exceptions.ResourceNotFoundException;
import com.example.gestaoestoque.repositories.MovementRepository;
import com.example.gestaoestoque.repositories.ProductRepository;
import com.example.gestaoestoque.services.MovementService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovementServiceImp implements MovementService {

  private final MovementRepository movementRepository;
  private final ProductRepository productRepository;
  private final ModelMapper mapper;
  @Override
  public MovementResponse registerMovement(MovementRequest movementRequest, Long productId) {
    Product product = productRepository
      .findById(productId)
      .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    switch (movementRequest.getType()){
      case INPUT -> product.input(movementRequest.getQuantity());
      case OUTPUT -> product.output(movementRequest.getQuantity());
    }

    Movement movement = mapper.map(movementRequest, Movement.class);
    movement.setProduct(product);
    movement.setDate(LocalDateTime.now());
    return mapper.map(movementRepository.save(movement), MovementResponse.class);
  }

  @Override
  public Page<MovementResponse> getMovementsInPeriod(
    LocalDate start,
    LocalDate end,
    Pageable pageable
  ) {
    return movementRepository
      .findMovementInPeriod(
        start.atStartOfDay(),
        end.atStartOfDay().plusDays(1),
        pageable
      )
      .map(movement -> mapper.map(movement, MovementResponse.class));
  }
}
