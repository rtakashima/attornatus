package com.attornatus.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.demo.dto.PessoaDTO;
import com.attornatus.demo.dto.SalvarPessoaDTO;
import com.attornatus.demo.entity.Pessoa;
import com.attornatus.demo.service.PessoaService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

  @Autowired
  private ModelMapper mapper;
  
  @Autowired
  private PessoaService pessoaService;
  
  @PostMapping("/")
  public void cadastrar(@Valid @RequestBody SalvarPessoaDTO pessoa) {
    log.info("Cadastrando Pessoa...");
    pessoaService.cadastrar(mapper.map(pessoa, Pessoa.class));
  }
  
  @PutMapping("/{id}")
  public void editar(@PathVariable("id") Long id, @Valid @RequestBody SalvarPessoaDTO pessoa) {
    log.info("Editando Pessoa ID={}", id);
    pessoaService.editar(id, mapper.map(pessoa, Pessoa.class));
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<PessoaDTO> consultar(@PathVariable("id") Long id) {
    log.info("Consultando Pessoa ID={}", id);
    var pessoa = mapper.map(pessoaService.consultar(id), PessoaDTO.class);
    return ResponseEntity.ok(pessoa);
  }
  
  @GetMapping("/")
  @SuppressWarnings("unchecked")
  public ResponseEntity<List<PessoaDTO>> listar() {
    log.info("Listando Pessoas...");
    var pessoas = pessoaService.listar();
    return ResponseEntity.ok(mapper.map(pessoas, List.class));
  }
  
}
