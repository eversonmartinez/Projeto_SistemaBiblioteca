package com.model;


import jakarta.persistence.*;
import lombok.Getter;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
//já existe um construtor sem parâmetros
@Entity
public class Livro implements Serializable {
    @Id
    @SequenceGenerator(name="seq_livro", sequenceName = "seq_livro_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_livro", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;
    @Min(value = 0)
    @Column(name = "ano", length = 4, nullable = false)
    private Integer ano;
    @Column(name = "edicao", length = 20, nullable = false)
    private String edicao;
    @ManyToOne
    @JoinColumn(name = "genero", referencedColumnName = "id", nullable = false)
    private Genero genero;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor", referencedColumnName = "id", nullable = false)
    private Autor autor;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Copia> copias;

    public Livro(){
        this.copias = new ArrayList<>();
    }

    public Livro(String nome, Integer ano, String edicao, Genero genero, Autor autor) {
        this.nome = nome;
        this.ano = ano;
        this.edicao = edicao;
        this.genero = genero;
        this.autor = autor;
        this.copias = new ArrayList<>();
        this.copias.add(new Copia());
    }

    public Livro(String nome, Integer ano, String edicao, Genero genero, Autor autor, Integer qtdCopias) {
        this.nome = nome;
        this.ano = ano;
        this.edicao = edicao;
        this.genero = genero;
        this.autor = autor;
        this.copias = new ArrayList<>();
        this.copias.add(new Copia());
        for(int i =0; i<qtdCopias; i++)
            this.copias.add(new Copia());
    }

    public void adicionarCopia(){
        this.copias.add(new Copia());
    }

    public void removerCopia(Copia copia){
        this.copias.remove(copia);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id) && Objects.equals(nome, livro.nome) && Objects.equals(edicao, livro.edicao) && Objects.equals(autor, livro.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, edicao, autor);
    }
}
