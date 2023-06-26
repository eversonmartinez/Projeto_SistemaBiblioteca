package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LivroTest {

    Livro livro;
    Livro livro2;
    Genero genero;
    Autor autor;
    @BeforeEach
    void setup(){
        genero = new Genero("Comédia");
        autor = new Autor("João", "Silva");
        livro = new Livro("Histórias", 2000, "1", genero, autor);
        livro2 = new Livro("Geografias", 2000, "1", genero, autor, 10);
    }

    @Test
    void adicionarCopia() {
    }

    @Test
    void removerCopia() {
    }

    @Test
    void modificarEmprestabilidadeCopia() {
    }

    @Test
    void adicionarNCopias() {
    }

    @Test
    void adicionarAutor() {
    }

    @Test
    void getId() {
    }

    @Test
    void getNome() {
    }

    @Test
    void getAno() {
    }

    @Test
    void getEdicao() {
    }

    @Test
    void getGenero() {
    }

    @Test
    void getAutores() {
    }

    @Test
    void getCopias() {
    }
}