package com.attornatus.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.attornatus.demo.entity.Endereco;
import com.attornatus.demo.entity.Pessoa;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EnderecoRepositoryTest {
  
  @Autowired
  private EnderecoRepository enderecoRepository;

  @Autowired
  private PessoaRepository pessoaRepository;

  private Pessoa pessoaDB;
  
  @BeforeEach
  void cadastrarPessoa() {
    pessoaDB = pessoaRepository.save(mockPessoaEndereco());
  }
  
  @Test
  @Order(1)
  void cadastrarEndereco() {
    Pessoa pessoa = pessoaRepository.findById(pessoaDB.getId()).orElseThrow();
    var endereco = mockEndereco();
    pessoa.getEnderecos().add(endereco);
    endereco.setPessoa(pessoa);
    
    assertThat(enderecoRepository.save(endereco)).usingRecursiveComparison().isEqualTo(endereco);
  }

  @Test
  @Order(2)
  void cadastrarEnderecoSecundario() {
    Pessoa pessoa = pessoaRepository.findById(pessoaDB.getId()).orElseThrow();
    
    Endereco endereco = new Endereco();
    endereco.setCep("04342010");
    endereco.setCidade("São Paulo");
    endereco.setLogradouro("Av Pedro Bueno");
    endereco.setNumero(169);
    
    pessoa.getEnderecos().add(endereco);
    endereco.setPessoa(pessoa);
    
    assertThat(enderecoRepository.save(endereco)).usingRecursiveComparison().isEqualTo(endereco);
  }
  
  @Test
  @Order(3)
  void listarEnderecos() {
    List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoaDB.getId());

    assertEquals(1, enderecos.size());
  }
  
  @Test
  @Order(4)
  void consultarEnderecoPrincipal() {
    Endereco enderecoPrincipal = enderecoRepository.findByPessoaIdAndIsPrincipalTrue(pessoaDB.getId());
    assertThat(enderecoPrincipal).isNotNull();
    assertEquals("Av Duque de Caxias", enderecoPrincipal.getLogradouro());
    assertEquals("68741360", enderecoPrincipal.getCep());
    assertEquals(479, enderecoPrincipal.getNumero());
    assertEquals("Castanhal", enderecoPrincipal.getCidade());
  }
  
  private Pessoa mockPessoa() {
    Pessoa pessoa = new Pessoa();
    pessoa.setNome("Rafael Takashima");
    pessoa.setDtNasc(LocalDate.of(1982, 6, 1));
    
    return pessoa;
  }

  private Pessoa mockPessoaEndereco() {
    Pessoa pessoa = mockPessoa();
    Endereco endereco = mockEndereco();
    pessoa.getEnderecos().add(endereco);
    endereco.setPessoa(pessoa);
    
    return pessoa;
  }
  
  private Endereco mockEndereco() {
    var endereco = new Endereco();
    endereco.setCep("68741360");
    endereco.setCidade("Castanhal");
    endereco.setIsPrincipal(true);
    endereco.setLogradouro("Av Duque de Caxias");
    endereco.setNumero(479);

    return endereco;
  }
}
