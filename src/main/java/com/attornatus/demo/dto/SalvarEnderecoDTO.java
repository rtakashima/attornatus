package com.attornatus.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SalvarEnderecoDTO {
  @NotBlank(message = "Informe o logradouro")
  @Size(min = 1, max = 100, message = "O Nome deve possuir de 1 a 100 caracteres")
  private String logradouro;
  
  @NotNull(message = "Informe o CEP")
  @Size(min = 8, max = 8, message = "O CEP informado e invalido.")
  private String cep;
  
  @NotNull(message = "Informe o Numero")
  @Positive(message = "Numero invalido")
  private Integer numero;
  
  @NotBlank(message = "Informe a cidade")
  private String cidade;

  private Boolean isPrincipal;
}
