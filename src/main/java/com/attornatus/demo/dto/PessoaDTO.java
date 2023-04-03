package com.attornatus.demo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PessoaDTO {
  private Long id;
  private String nome;
  private LocalDate dtNasc;
}
