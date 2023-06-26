package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeneroTest {

    Genero genero;
    @BeforeEach
    void setup(){
        genero = new Genero("Comédia");
    }


    @Test
    void getNome() {
        assertEquals("Comédia", genero.getNome());
    }

    @Test
    void setNome() {
        genero.setNome("Terror");
        assertEquals("Terror", genero.getNome());
    }
}