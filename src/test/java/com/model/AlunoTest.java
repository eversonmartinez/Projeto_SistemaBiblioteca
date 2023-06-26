package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class AlunoTest {

    Aluno aluno;
    @BeforeEach
    void setup(){
        aluno = new Aluno("João", "22999999999", "joao@joao.com", "111213");
    }

    @Test
    void getMatricula() {
        assertEquals("111213", aluno.getMatricula());
    }

}