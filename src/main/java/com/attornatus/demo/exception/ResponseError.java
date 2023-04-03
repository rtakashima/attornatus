package com.attornatus.demo.exception;

import java.util.List;

import lombok.Data;

@Data
public class ResponseError {

  private Integer code;
  private List<ResponseMessage> messages;
}
