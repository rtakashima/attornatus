package com.attornatus.demo.exception;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
    log.error("MethodArgumentNotValid: Valor de argumento incorreto");
    var responseError = new ResponseError();
    responseError.setCode(HttpStatus.BAD_REQUEST.value());
    var messages = new ArrayList<ResponseMessage>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      messages.add(new ResponseMessage(fieldName, errorMessage));
    });

    responseError.setMessages(messages);

    return ResponseEntity.badRequest().body(responseError);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("IllegalArgument: Valor de parametro incorreto");
    return ResponseEntity.badRequest().body(getErrors(HttpStatus.BAD_REQUEST, "ID", ex));
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ResponseError> handleGeneralExceptions(Exception ex) {
    log.error("Exception", ex);
    return ResponseEntity.badRequest().body(getErrors(HttpStatus.INTERNAL_SERVER_ERROR, "error", ex));
  }

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<ResponseError> handleRuntimeExceptions(RuntimeException ex) {
    log.error("RuntimeException", ex);
    return ResponseEntity.badRequest().body(getErrors(HttpStatus.INTERNAL_SERVER_ERROR, "error", ex));
  }  
  
  /**
   * Faz o bind das messagens de erro
   * @param status
   * @param field
   * @param ex
   * @return
   */
  private ResponseError getErrors(HttpStatus status, String field, Exception ex) {
    var responseError = new ResponseError();
    responseError.setCode(status.value());
    var messages = new ArrayList<ResponseMessage>();
    messages.add(new ResponseMessage(field, ex.getMessage()));
    responseError.setMessages(messages);
    
    return responseError;
}  
}
