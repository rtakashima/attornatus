package com.attornatus.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.attornatus.demo.entity.Pessoa;
import com.attornatus.demo.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PessoaService service;

  @Test
  void cadastrarPessoa() throws Exception {
    when(service.cadastrar(any(Pessoa.class))).thenReturn(mockPessoa());
    mockMvc.perform(
        post("/pessoa/")
                .content(asJsonString(mockPessoa()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());
  }

  @Test
  void nomeInvalido() throws Exception {
    when(service.cadastrar(any(Pessoa.class))).thenReturn(mockPessoa3());
    mockMvc.perform(
        post("/pessoa/")
                .content(asJsonString(mockPessoa3()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest())
     .andExpect(content().string(containsString("Informe o nome")));
  }

  @Test
  void dtNascInvalida() throws Exception {
    when(service.cadastrar(any(Pessoa.class))).thenReturn(mockPessoa2());
    mockMvc.perform(
        post("/pessoa/")
                .content(asJsonString(mockPessoa2()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest())
     .andExpect(content().string(containsString("Informe a data de nascimento")));
  }

  @Test
  void editarPessoa() throws Exception {
    when(service.editar(anyLong(), any(Pessoa.class))).thenReturn(mockPessoa());
    mockMvc.perform(
        put("/pessoa/{id}", 1l)
                .content(asJsonString(mockPessoa()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());
  }

  @Test
  void consultarPessoa() throws Exception {
    when(service.consultar(anyLong())).thenReturn(mockPessoa());
    var res = mockMvc.perform(
        get("/pessoa/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk()).andReturn();
    
    Pessoa pessoa = jsonToObj(res.getResponse().getContentAsString(), Pessoa.class);
    assertThat(pessoa).usingRecursiveComparison().isEqualTo(mockPessoa());
  }

  @Test
  void listarPessoa() throws Exception {
    when(service.listar()).thenReturn(mockPessoas());
    var res = mockMvc.perform(
        get("/pessoa/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk()).andReturn();
    
    @SuppressWarnings("unchecked")
    List<Pessoa> pessoas = jsonToObj(res.getResponse().getContentAsString(), List.class);
    assertThat(pessoas).hasSize(3);
  }

  private List<Pessoa> mockPessoas() {
    List<Pessoa> pessoas = new ArrayList<>();
    pessoas.add(mockPessoa());
    pessoas.add(mockPessoa2());
    pessoas.add(mockPessoa3());
    
    return pessoas;
  }
  
  private Pessoa mockPessoa() {
    var pessoa = new Pessoa();
    pessoa.setNome("Rafael Takashima");
    pessoa.setDtNasc(LocalDate.of(1982, 6, 1));
    return pessoa;
  }

  private Pessoa mockPessoa2() {
    var pessoa = new Pessoa();
    pessoa.setNome("Bill Gates");
    return pessoa;
  }

  private Pessoa mockPessoa3() {
    var pessoa = new Pessoa();
    pessoa.setDtNasc(LocalDate.of(1970, 1, 1));
    return pessoa;
  }

  private String asJsonString(final Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      final String jsonContent = mapper.writeValueAsString(obj);
      return jsonContent;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private <T> T jsonToObj(String json, Class<T> clazz) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      return mapper.readValue(json, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
