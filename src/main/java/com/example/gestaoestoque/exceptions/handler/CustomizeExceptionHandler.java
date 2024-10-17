package com.example.gestaoestoque.exceptions.handler;

import com.example.gestaoestoque.exceptions.AbstractException;
import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class CustomizeExceptionHandler {

  @ExceptionHandler(AbstractException.class)
  public ResponseEntity<ExceptionResponse> handleExceptionAbstractException(
    AbstractException ex
  ) {
    log.error(ex.getMessage());
    return this.createResponse(
      ex,
      ex.getStatus(),
      ex.getMessage(),
      ex.getErrors()
    );
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(
    DataIntegrityViolationException ex
  ) {
    log.error(ex.getCause().getCause().getMessage(), ex.getMessage());
    return this.createResponse(
      ex,
      HttpStatus.BAD_REQUEST,
      "Some database constraint has been broken.",
      ex.getCause().getCause().getMessage()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex) {
    String[] errors = ex.getBindingResult().getAllErrors().stream()
      .map(e -> ((FieldError) e).getField() + " -> " + e.getDefaultMessage())
      .toArray(String[]::new);
    log.error(ex.getMessage());
    log.error(errors);
    return this.createResponse(
      ex,
      HttpStatus.BAD_REQUEST,
      "One or more arguments are invalid.",
      errors
    );
  }

  @ExceptionHandler(ValidationException.class)
  protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(
    ValidationException ex) {
    return this.createResponse(
      ex,
      HttpStatus.BAD_REQUEST,
      "One or more arguments are invalid.",
      ex.getCause().getMessage()
    );
  }



  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleAllException(
    Exception ex
  ) {
    log.error(ex.getMessage());
    return this.createResponse(
      ex,
      HttpStatus.INTERNAL_SERVER_ERROR,
      "An unknown error has occurred.",
      ex.getMessage()
    );
  }

  private ResponseEntity<ExceptionResponse> createResponse(Exception ex, HttpStatus status,
    String message, String... errors) {
    ExceptionResponse apiError = new ExceptionResponse(
      status,
      message,
      this.causedIn(ex),
      errors
    );
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  private String causedIn(Exception ex) {
    StringBuilder cause = new StringBuilder();
    StackTraceElement ste = ex.getStackTrace()[0];
    cause.append("Classe: ").append(ste.getClassName()).append(" -  Linha: ")
      .append(ste.getLineNumber());
    return cause.toString();
  }

}
