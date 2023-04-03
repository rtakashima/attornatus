package com.attornatus.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.attornatus.demo.entity.Endereco;
import com.attornatus.demo.entity.Pessoa;
import com.attornatus.demo.repository.EnderecoRepository;
import com.attornatus.demo.repository.PessoaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EnderecoServiceImpl implements EnderecoService {

  @Autowired
  private EnderecoRepository enderecoRepository;
  @Autowired
  private PessoaRepository pessoaRepository;

  @Override
  public Endereco cadastrar(Long idPessoa, Endereco endereco) {
    log.info("Cadastrando endereco da pessoa ID={}", idPessoa);
    Pessoa pessoaDB = pessoaRepository.findById(idPessoa)
        .orElseThrow(() -> new IllegalArgumentException("ID invalido"));

    log.info("Pessoa encontrada");
    // se tiver somente 1 endereco, entao eh o principal
    if (CollectionUtils.isEmpty(pessoaDB.getEnderecos())) {
      pessoaDB.setEnderecos(new ArrayList<>());
      endereco.setIsPrincipal(true);
    }
    // se o ultimo endereco for o principal, remover principal de outros
    else if (Boolean.TRUE.equals(endereco.getIsPrincipal())) {
      pessoaDB.getEnderecos().forEach(e -> e.setIsPrincipal(false));
    }

    pessoaDB.getEnderecos().add(endereco);
    endereco.setPessoa(pessoaDB);

    log.info("Salvando novo endereco");
    return enderecoRepository.save(endereco);
  }

  @Override
  public List<Endereco> listar(Long idPessoa) {
    log.info("Listando enderecos da pessoa ID={}", idPessoa);
    return enderecoRepository.findByPessoaId(idPessoa);
  }

  @Override
  public Endereco consultarPrincipal(Long idPessoa) {
    log.info("Consultando endereco principal da pessoa ID={}", idPessoa);
    return enderecoRepository.findByPessoaIdAndIsPrincipalTrue(idPessoa);
  }

}
