package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeitorTest {

    Leitor leitor;
    @BeforeEach
    void setup(){
        leitor = new Leitor("João", "22999999999", "joao@joao.com");
    }

    @Test
    void criarUsuario() {
        leitor.criarUsuario("joao", "123");
        assertEquals( "joao", leitor.getUsuario().getLogin());
    }

    @Test
    void alterarSenha() {
        leitor.criarUsuario("joao", "123");
        leitor.alterarSenha("1234");
        assertEquals( "1234", leitor.getUsuario().getSenha());
    }

    @Test
    void excluirUsuario() {
        leitor.criarUsuario("joao", "123");
        leitor.excluirUsuario();
        assertEquals(null, leitor.getUsuario());
    }

    @Test
    void getNome() {
        assertEquals("João", leitor.getNome());
    }

    @Test
    void getTelefone() {
        assertEquals("22999999999", leitor.getTelefone());
    }

    @Test
    void getEmail() {
        assertEquals("joao@joao.com", leitor.getEmail());
    }

    @Test
    void setTelefone() {
        leitor.setTelefone("22999888888");
        assertEquals("22999888888", leitor.getTelefone());
    }

    @Test
    void setEmail() {
        leitor.setEmail("joaozim@joao.com");
        assertEquals("joaozim@joao.com", leitor.getEmail());
    }
}