package com.example.gestaoestoque.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends AbstractException {

  public ResourceAlreadyExistsException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
