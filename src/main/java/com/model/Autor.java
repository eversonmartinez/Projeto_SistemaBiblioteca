package com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Autor {
    @Id
    @SequenceGenerator(name="seq_autor", sequenceName="seq_autor_id", allocationSize = 1)
    @GeneratedValue(generator="seq_autor", strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name="nome", length=20, nullable = false)
    @Setter
    private String nome;
    @Column(name="sobreNome", length=30, nullable = false)
    @Setter
    private String sobreNome;

    public Autor(String nome, String sobreNome) {
        this.nome = nome;
        this.sobreNome = sobreNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(id, autor.id) && Objects.equals(nome, autor.nome) && Objects.equals(sobreNome, autor.sobreNome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobreNome);
    }
}
