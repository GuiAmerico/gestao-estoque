package com.example.gestaoestoque.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotValidException extends AbstractException {

  public ResourceNotValidException(String message, Object... args) {
    super(String.format(message,args), HttpStatus.BAD_REQUEST);
  }
}
