package com.attornatus.demo.service;

import java.util.List;

import com.attornatus.demo.entity.Endereco;

public interface EnderecoService {
  /**
   * Criar novo endereco de pessoa
   * @param idPessoa
   * @param endereco
   * @return endereco criado
   */
  Endereco cadastrar(Long idPessoa, Endereco endereco);

  /**
   * Listar enderecos de pessoa
   * @param idPessoa
   * @return Lista de enderecos da pessoa
   */
  List<Endereco> listar(Long idPessoa);
  
  /**
   * Consultar endereco principal
   * @param idPessoa
   * @return endereco pricipal
   */
  Endereco consultarPrincipal(Long idPessoa);
  
}
