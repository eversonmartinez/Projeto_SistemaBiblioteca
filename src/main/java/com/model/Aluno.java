package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
public class Aluno extends Leitor implements Serializable{
    @Column(name = "matricula", length = 10, nullable = false)
    private String matricula;

    public Aluno(String nome, String telefone, String email, String matricula) {
        super(nome, telefone, email);
        this.matricula = matricula;
    }
}
