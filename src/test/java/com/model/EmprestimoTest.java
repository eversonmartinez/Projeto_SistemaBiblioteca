package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    Emprestimo emprestimo;
    Emprestimo emprestimo2;
    Aluno aluno;
    @BeforeEach
    void setup(){
        aluno = new Aluno("João", "22999999999", "joao@joao.com", "2222222222");
        Professor professor = new Professor("João", "22999999999", "joao@joao.com", "Analista");
        emprestimo = new Emprestimo(LocalDate.of(2023, 06, 06), new Copia(true), aluno);
        emprestimo2 = new Emprestimo(LocalDate.of(2023, 06, 06), new Copia(true), professor);
    }

    @Test
    void emprestimoCopiaNaoEmprestavel() {
        assertThrows(IllegalArgumentException.class,  () -> new Emprestimo(LocalDate.of(2023, 06, 06), new Copia(false), aluno));
    }

    @Test
    void setDataEntrega() {
        emprestimo.setDataEntrega(LocalDate.of(2023, 06, 10));
        assertEquals(LocalDate.of(2023, 06, 10), emprestimo.getDataEntrega());
    }

    @Test
    void getData() {
        assertEquals(LocalDate.of(2023, 06, 06), emprestimo.getData());
    }

    @Test
    void getDataPrevistaEntregaAluno() {
        assertEquals(LocalDate.of(2023, 06, 11), emprestimo.getDataPrevistaEntrega());
    }

    @Test
    void getDataPrevistaEntregaProfessor() {
        assertEquals(LocalDate.of(2023, 07, 06), emprestimo2.getDataPrevistaEntrega());
    }


    @Test
    void getLeitor() {
        assertEquals(aluno, emprestimo.getLeitor());
    }
}