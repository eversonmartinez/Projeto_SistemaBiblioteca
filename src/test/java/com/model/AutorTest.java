package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {

    Autor autor;
    @BeforeEach
    void setup(){
        autor = new Autor("João", "Silva");
    }

    @Test
    void getNome() {
        assertEquals( "João", autor.getNome());
    }

    @Test
    void getSobreNome() {
        assertEquals( "Silva", autor.getSobreNome());
    }

    @Test
    void setNome() {
        autor.setNome("Maria");
        assertEquals( "Maria", autor.getNome());
    }

    @Test
    void setSobreNome() {
        autor.setNome("Santos");
        assertEquals( "Santos", autor.getNome());
    }
}