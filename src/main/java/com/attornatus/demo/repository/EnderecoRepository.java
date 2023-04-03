package com.attornatus.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.demo.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

  /**
   * Lista os enderecos de uma pessoa
   * @param id
   * @return list de enderecos
   */
  List<Endereco> findByPessoaId(Long id);
  
  /**
   * Busca o endereco principal de uma pessoa
   * @param id
   * @return
   */
  Endereco findByPessoaIdAndIsPrincipalTrue(Long id);
}
