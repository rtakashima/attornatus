package com.attornatus.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.attornatus.demo.entity.Endereco;
import com.attornatus.demo.entity.Pessoa;
import com.attornatus.demo.repository.EnderecoRepository;
import com.attornatus.demo.repository.PessoaRepository;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {
  
  @InjectMocks
  private EnderecoService service = new EnderecoServiceImpl();
  
  @InjectMocks
  private PessoaService pessoaService = new PessoaServiceImpl();
  
  @Mock
  private EnderecoRepository repository;
  
  @Mock
  private PessoaRepository pessoaRepository;
  
  @Test
  void cadastrarEndereco() {
    //pre-condicao
    final var endereco = mockEndereco();
    final var pessoa = mockPessoa();
    when(repository.save(any(Endereco.class))).thenReturn(endereco);
    when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));
    
    //acao
    final var enderecoDB = service.cadastrar(1l, new Endereco());
    
    //verificacao
    assertThat(enderecoDB).usingRecursiveComparison().isEqualTo(endereco);
  }

  @Test
  void listarEnderecos() {
    //pre-condicao
    when(repository.findByPessoaId(anyLong())).thenReturn(mockEnderecos());
    
    //acao
    final var enderecosDB = service.listar(1l);
    
    //verificacao
    assertThat(enderecosDB).hasSize(2);
  }

  @Test
  void consultarEnderecoPrincipal() {
    //pre-condicao
    when(repository.findByPessoaIdAndIsPrincipalTrue(anyLong())).thenReturn(mockEndereco());
    
    //acao
    final var enderecoDB = service.consultarPrincipal(1l);
    
    //verificacao
    assertThat(enderecoDB).usingRecursiveComparison().isEqualTo(mockEndereco());
  }
  
  private List<Endereco> mockEnderecos() {
    List<Endereco> enderecos = new ArrayList<>();
    enderecos.add(mockEndereco());
    enderecos.add(mockEndereco2());
    
    return enderecos;
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
  
  private Endereco mockEndereco2() {
    var endereco = new Endereco();
    endereco.setCep("04342010");
    endereco.setCidade("São Paulo");
    endereco.setLogradouro("Av Pedro Bueno");
    endereco.setNumero(169);
    
    return endereco;
  }
  
  private Pessoa mockPessoa() {
    var pessoa = new Pessoa();
    pessoa.setNome("Rafael Takashima");
    pessoa.setDtNasc(LocalDate.of(1982, 6, 1));
    
    return pessoa;
  }

}
