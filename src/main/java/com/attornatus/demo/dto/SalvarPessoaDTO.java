package com.attornatus.demo.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SalvarPessoaDTO {
  @NotNull(message = "Informe o nome")
  @Size(min = 2, max = 50, message = "O Nome deve possuir de 2 a 50 caracteres")
  private String nome;
  
  @NotNull(message = "Informe a data de nascimento")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @JsonFormat(pattern="dd/MM/yyyy")
  private LocalDate dtNasc;
  
  private List<SalvarEnderecoDTO> enderecos; 

}
