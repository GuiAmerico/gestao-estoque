package com.example.gestaoestoque.controllers.response;

import com.example.gestaoestoque.entities.enums.MovementType;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovementResponse {

  private Long id;
  @JsonIncludeProperties({"id", "name", "stock"})
  private ProductResponse product;
  private int quantity;
  private LocalDateTime date;
  private MovementType type;
}
