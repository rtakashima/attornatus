package com.attornatus.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.attornatus.demo.entity.Pessoa;
import com.attornatus.demo.repository.PessoaRepository;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {
  
  @InjectMocks
  private PessoaService service = new PessoaServiceImpl();
  
  @Mock
  private PessoaRepository repository;
  
  @Test
  void cadastrarPessoa() {
    //pre-condicao
    final var pessoa = mockPessoa();
    when(repository.save(any(Pessoa.class))).thenReturn(pessoa);
    
    //acao
    final var pessoaDB = service.cadastrar(new Pessoa());
    
    //verificacao
    assertThat(pessoaDB).usingRecursiveComparison().isEqualTo(pessoa);
  }

  @Test
  void consultarPessoa() {
    //pre-condicao
    final var pessoa = mockPessoa();
    when(repository.findById(anyLong())).thenReturn(Optional.of(pessoa));
    
    //acao
    final var pessoaDB = service.consultar(getRandomLong());
    
    //verificacao
    assertThat(pessoaDB).usingRecursiveComparison().isEqualTo(pessoa);
  }

  @Test
  void editarPessoa() {
    //pre-condicao
    final var pessoa = mockPessoa();
    final var pessoa2 = mockPessoa2();
    when(repository.findById(anyLong())).thenReturn(Optional.of(pessoa));
    when(repository.save(any(Pessoa.class))).thenReturn(pessoa2);
    
    //acao
    final var pessoaDB = service.editar(1l, pessoa2);
    
    //verificacao
    assertThat(pessoaDB).usingRecursiveComparison().isEqualTo(pessoa2);
  }
  
  @Test
  void listarPessoas() {
    //pre-condicao
    when(repository.findAll()).thenReturn(mockPessoas());
    
    //acao
    final var pessoasDB = service.listar();
    
    //verificacao
    assertThat(pessoasDB).hasSize(2);
  }
  
  private List<Pessoa> mockPessoas() {
    List<Pessoa> pessoas = new ArrayList<>();
    pessoas.add(mockPessoa());
    pessoas.add(mockPessoa2());
    
    return pessoas;
  }
  
  private Pessoa mockPessoa() {
    var pessoa = new Pessoa();
    pessoa.setNome("Rafael Takashima");
    pessoa.setDtNasc(LocalDate.of(1982, 6, 1));
    
    return pessoa;
  }
  
  private Pessoa mockPessoa2() {
    var pessoa2 = new Pessoa();
    pessoa2.setNome("Goku");
    pessoa2.setDtNasc(LocalDate.of(737, 4, 16));
    
    return pessoa2;
  }
  
  private long getRandomLong() {
    return (long)new Random().ints(1, 10).findFirst().getAsInt();
  }  
}
