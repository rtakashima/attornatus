package com.attornatus.demo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

/**
 * Entidade representativa de Pessoa
 * @author takashima
 * @version 1.0.0
 */
@Data
@Entity
public class Pessoa {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private Long id;
  
  @Column(length = 50, nullable = false)
  private String nome;
  
  @Temporal(TemporalType.DATE)
  @Column(nullable = false)
  private LocalDate dtNasc;
  
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Endereco> enderecos;
  
  public Pessoa() {
    this.enderecos = new ArrayList<>();
  }
}
