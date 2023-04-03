package com.attornatus.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.attornatus.demo.entity.Endereco;
import com.attornatus.demo.entity.Pessoa;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PessoaRepositoryTest {

  @Autowired
  private PessoaRepository pessoaRepository;

  private Pessoa pessoaDB;
  
  @BeforeEach
  void cadastrarPessoa() {
    pessoaDB = pessoaRepository.save(mockPessoa());
  }
  
  @Test
  @Order(1)
  @Rollback(false)
  void cadastrarPessoaTest() {
    assertEquals("Rafael Takashima", pessoaDB.getNome());
    assertEquals(LocalDate.of(1982, 6, 1), pessoaDB.getDtNasc());
  }

  @Test
  @Order(2)
  void buscarPessoa() {
    Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaDB.getId());
    var p = mockPessoa();

    assertTrue(pessoa.isPresent());
    assertEquals(p.getNome(), pessoa.get().getNome());
    assertEquals(p.getDtNasc(), pessoa.get().getDtNasc());
    assertThat(p.getEnderecos()).hasSize(1);
  }

  @Test
  @Order(3)
  void editarPessoa() {
    Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaDB.getId());
    assertTrue(pessoa.isPresent());
    
    Pessoa p = pessoa.get();
    p.setNome("Tony Stark");
    p.setDtNasc(LocalDate.of(1970, 5, 29));
    
    assertDoesNotThrow(() -> pessoaRepository.save(p));
    
    var p2 = pessoaRepository.findById(pessoaDB.getId());

    assertTrue(p2.isPresent());
    assertEquals("Tony Stark", p2.get().getNome());
    assertEquals(LocalDate.of(1970, 5, 29), p2.get().getDtNasc());
  }

  @Test
  @Order(4)
  void listarPessoas() {
    List<Pessoa> pessoas = pessoaRepository.findAll();
    assertThat(pessoas).hasSizeGreaterThanOrEqualTo(1);
  }

  private Pessoa mockPessoa() {
    var pessoa = new Pessoa();
    pessoa.setNome("Rafael Takashima");
    pessoa.setDtNasc(LocalDate.of(1982, 6, 1));

    Endereco endereco = new Endereco();
    endereco.setCep("68741360");
    endereco.setCidade("Castanhal");
    endereco.setIsPrincipal(true);
    endereco.setLogradouro("Av Duque de Caxias");
    endereco.setNumero(479);
    endereco.setPessoa(pessoa);

    pessoa.getEnderecos().add(endereco);
    
    return pessoa;
  }
  
}
