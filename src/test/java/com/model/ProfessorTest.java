package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {

    Professor professor;

    @BeforeEach
    void setup(){
        professor = new Professor("João", "22999999999", "joao@joao.com", "Analista");
    }

    @Test
    void getFormacao() {
        assertEquals("Analista", professor.getFormacao());
    }

    @Test
    void setFormacao() {
        professor.setFormacao("Desanalista");
        assertEquals("Desanalista", professor.getFormacao());
    }
}