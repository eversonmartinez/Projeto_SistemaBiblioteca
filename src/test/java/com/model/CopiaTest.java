package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CopiaTest {
    Copia copia;
    @BeforeEach
    void setup(){
        copia = new Copia();
    }

    @Test
    void emprestavelConstrutorVazio() {
        assertEquals(false, copia.getEmprestavel());
    }

    @Test
    void emprestavelConstrutorAlterado() {
        Copia copia2 = new Copia(true);
        assertEquals(true, copia2.getEmprestavel());
    }

    @Test
    void setEmprestavel() {
        copia.setEmprestavel(true);
        assertEquals(true, copia.getEmprestavel());
    }

}