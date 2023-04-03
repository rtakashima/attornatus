package com.attornatus.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.attornatus.demo.entity.Pessoa;

@Service
public interface PessoaService {
  /**
   * Criar novo registro de pessoa
   * @param pessoa
   * @return pessoa criada
   */
  Pessoa cadastrar(Pessoa pessoa);

  /**
   * Atualizar registro de pessoa
   * @param id pessoa
   * @param pessoa novos dados
   * @return pessoa editada
   */
  Pessoa editar(Long id, Pessoa pessoa);
  
  /**
   * Consultar pessoa
   * @param id pessoa
   * @return pessoa encontrada
   */
  Pessoa consultar(Long id);
  
  /**
   * Listar pessoas
   * @return pessoas encontradas
   */
  List<Pessoa> listar();
}
