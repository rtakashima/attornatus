package com.attornatus.demo.entity;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Entidade representativa de Endereço
 * @author takashima
 * @version 1.0.0
 */

@Data
@Entity
public class Endereco {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private Long id;

  @Column(length = 100, nullable = false)
  private String logradouro;
  
  @Column(length = 8, nullable = false)
  private String cep;
  @Column(length = 8, nullable = false)
  private Integer numero;
  
  @Column(length = 50, nullable = false)
  private String cidade;

  private Boolean isPrincipal;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
  private Pessoa pessoa;
}
