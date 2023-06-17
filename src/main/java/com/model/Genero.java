package com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Genero implements Serializable {
    @Id
    @SequenceGenerator(name="seq_genero", sequenceName = "seq_genero_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_genero", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "nome", length = 30, nullable = false)
    @Setter
    private String nome;

    public Genero(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        return Objects.equals(id, genero.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " | " + nome ;
    }
}
