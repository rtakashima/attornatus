package com.attornatus.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.attornatus.demo.entity.Pessoa;
import com.attornatus.demo.repository.PessoaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PessoaServiceImpl implements PessoaService {

  @Autowired
  private PessoaRepository pessoaRepository;

  @Override
  public Pessoa cadastrar(Pessoa pessoa) {
    log.info("Cadastrando Pessoa...");
    if (pessoa == null) {
      return null;
    }
    
    if (!CollectionUtils.isEmpty(pessoa.getEnderecos())) {
      pessoa.getEnderecos().forEach(e -> e.setPessoa(pessoa));
      var count = pessoa.getEnderecos().stream().filter(e->Boolean.TRUE.equals(e.getIsPrincipal())).count();
      log.info("Quantidade de enderecos principais atualmente: {}", count);
      
      //se nenhum endereco foi marcado como principal, entao o principal sera o primeiro
      if (count == 0) {
        pessoa.getEnderecos().get(0).setIsPrincipal(true);
      }
    }
    
    log.info("Salvando Pessoa...");
    return pessoaRepository.save(pessoa);
  }

  @Override
  public Pessoa editar(Long id, Pessoa pessoa) {
    log.info("Editando Pessoa...");
    Pessoa pessoaDB = pessoaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID invalido"));
    
    log.info("Pessoa encontrada");
    pessoaDB.setNome(pessoa.getNome());
    pessoaDB.setDtNasc(pessoa.getDtNasc());

    log.info("Salvando Pessoa...");
    return pessoaRepository.save(pessoaDB);
  }

  @Override
  public Pessoa consultar(Long id) {
    log.info("Consultando Pessoa ID={}", id);
    return pessoaRepository.findById(id).orElse(null);
  }

  @Override
  public List<Pessoa> listar() {
    log.info("Listando pessoas...");
    return pessoaRepository.findAll();
  }

}
