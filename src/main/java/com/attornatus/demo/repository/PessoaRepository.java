package com.attornatus.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.attornatus.demo.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

  Pessoa findByNome(String nome);
}
