package com.attornatus.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.attornatus.demo.entity.Endereco;
import com.attornatus.demo.service.EnderecoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {
  
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EnderecoService service;

  @Test
  void cadastrarEndereco() throws Exception {
    when(service.cadastrar(anyLong(), any(Endereco.class))).thenReturn(mockEndereco());
    mockMvc.perform(
        post("/endereco/{id}", 1l)
                .content(asJsonString(mockEndereco()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk());
  }
  
  @Test
  void cepInvalido() throws Exception {
    when(service.cadastrar(anyLong(), any(Endereco.class))).thenReturn(mockEndereco());
    mockMvc.perform(
        post("/endereco/{id}", 1l)
                .content(asJsonString(mockEndereco3()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest())
     .andExpect(content().string(containsString("O CEP informado e invalido")));
  }

  @Test
  void numeroInvalido() throws Exception {
    when(service.cadastrar(anyLong(), any(Endereco.class))).thenReturn(mockEndereco());
    mockMvc.perform(
        post("/endereco/{id}", 1l)
                .content(asJsonString(mockEndereco4()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest())
     .andExpect(content().string(containsString("Numero invalido")));
  }

  @Test
  void cidadeInvalida() throws Exception {
    when(service.cadastrar(anyLong(), any(Endereco.class))).thenReturn(mockEndereco());
    mockMvc.perform(
        post("/endereco/{id}", 1l)
                .content(asJsonString(mockEndereco5()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isBadRequest())
     .andExpect(content().string(containsString("Informe a cidade")));
  }
  
  @Test
  void consultarPrincipal() throws Exception {
    when(service.consultarPrincipal(anyLong())).thenReturn(mockEndereco());
    var res = mockMvc.perform(
        get("/endereco/consultar/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk()).andReturn();
    
    Endereco endereco = jsonToObj(res.getResponse().getContentAsString(), Endereco.class);
    assertThat(endereco).usingRecursiveComparison().ignoringFields("isPrincipal").isEqualTo(mockEndereco());
  }
  
  @Test
  void listarEndereco() throws Exception {
    when(service.listar(anyLong())).thenReturn(mockEnderecos());
    var res = mockMvc.perform(
        get("/endereco/listar/{id}", 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk()).andReturn();
    
    @SuppressWarnings("unchecked")
    List<Endereco> enderecos = jsonToObj(res.getResponse().getContentAsString(), List.class);
    assertThat(enderecos).hasSize(5);
  }
  
  private List<Endereco> mockEnderecos() {
    List<Endereco> enderecos = new ArrayList<>();
    enderecos.add(mockEndereco());
    enderecos.add(mockEndereco2());
    enderecos.add(mockEndereco3());
    enderecos.add(mockEndereco4());
    enderecos.add(mockEndereco5());
    
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

  private Endereco mockEndereco3() {
    var endereco = new Endereco();
    endereco.setCep("043420");
    endereco.setCidade("São Paulo");
    endereco.setLogradouro("Av Liberdade");
    endereco.setNumero(200);
    
    return endereco;
  }
  
  private Endereco mockEndereco4() {
    var endereco = new Endereco();
    endereco.setCep("04342010");
    endereco.setCidade("São Paulo");
    endereco.setLogradouro("Av Pedro Bueno");
    endereco.setNumero(-100);
    
    return endereco;
  }
  
  private Endereco mockEndereco5() {
    var endereco = new Endereco();
    endereco.setCep("04342010");
    endereco.setCidade("");
    endereco.setLogradouro("Av Pedro Bueno");
    endereco.setNumero(100);
    
    return endereco;
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
