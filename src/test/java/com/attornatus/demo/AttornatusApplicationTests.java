package com.attornatus.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.attornatus.demo.controller.EnderecoController;
import com.attornatus.demo.controller.PessoaController;
import com.attornatus.demo.service.EnderecoService;
import com.attornatus.demo.service.PessoaService;

@SpringBootTest
@ActiveProfiles("test")
class AttornatusApplicationTests {

  @Autowired
  private PessoaController pessoaController;
  
  @Autowired
  private EnderecoController enderecoController;
  
  @Autowired
  private PessoaService pessoaService;
  
  @Autowired
  private EnderecoService enderecoService;
  
	@Test
	void contextLoads() {
	  assertThat(pessoaController).isNotNull();
    assertThat(enderecoController).isNotNull();
    assertThat(pessoaService).isNotNull();
    assertThat(enderecoService).isNotNull();
	}

}
