package com.attornatus.demo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attornatus.demo.dto.EnderecoDTO;
import com.attornatus.demo.dto.SalvarEnderecoDTO;
import com.attornatus.demo.entity.Endereco;
import com.attornatus.demo.service.EnderecoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/endereco")
public class EnderecoController {
  
  @Autowired
  private ModelMapper mapper;
  
  @Autowired
  private EnderecoService enderecoService;
  
  @PostMapping("/{id}")
  public void cadastrar(@PathVariable("id") Long idPessoa, @Valid @RequestBody SalvarEnderecoDTO endereco) {
    log.info("Cadastrando endereco da pessoa ID={}", idPessoa);
    enderecoService.cadastrar(idPessoa, mapper.map(endereco, Endereco.class));
  }
  
  @GetMapping("/consultar/{id}")
  public ResponseEntity<EnderecoDTO> consultar(@PathVariable("id") Long idPessoa) {
    log.info("Consultando endereco principal da pessoa ID={}", idPessoa);
    var endereco = mapper.map(enderecoService.consultarPrincipal(idPessoa), EnderecoDTO.class);
    return ResponseEntity.ok(endereco);
  }
  
  @GetMapping("/listar/{id}")
  @SuppressWarnings("unchecked")
  public ResponseEntity<List<EnderecoDTO>> listar(@PathVariable("id") Long idPessoa) {
    log.info("Listando enderecos da pessoa ID={}", idPessoa);
    var enderecos = enderecoService.listar(idPessoa);
    return ResponseEntity.ok(mapper.map(enderecos, List.class));
  }

}
