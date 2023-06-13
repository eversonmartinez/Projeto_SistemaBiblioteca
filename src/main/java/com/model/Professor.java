package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Professor extends Leitor implements Serializable {
    @Column(name = "formacao", length = 50, nullable = false)
    private String formacao;

    public Professor(String nome, String telefone, String email, String formacao) {
        super(nome, telefone, email);
        this.formacao = formacao;
    }
}
