package com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Emprestimo implements Serializable {
    @Id
    @SequenceGenerator(name="seq_emprestimo", sequenceName="seq_emprestimo_id", allocationSize = 1)
    @GeneratedValue(generator="seq_emprestimo", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name="data", nullable = false)
    private LocalDate data;
    @Column(name = "dataPrevistaEntrega", nullable = false)
    private LocalDate dataPrevistaEntrega;
    @Column(name="dataEntrega", nullable = true)
    private LocalDate dataEntrega;
    @ManyToOne
    @JoinColumn(name = "copai", nullable = false)
    private Copia copia;
    @ManyToOne
    @JoinColumn(name = "leitor", nullable = false)
    private Leitor leitor;

    public Emprestimo(LocalDate data, Copia copia, Leitor leitor) {
        this.data = data;
        this.copia = copia;
        this.leitor = leitor;
        if(this.leitor.getClass() == Professor.class){
            this.dataPrevistaEntrega = this.data.plusDays(30);
        }
        if(this.leitor.getClass() == Aluno.class){
            this.dataPrevistaEntrega = this.data.plusDays(5);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return Objects.equals(id, that.id) && Objects.equals(data, that.data) && Objects.equals(copia, that.copia) && Objects.equals(leitor, that.leitor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, copia, leitor);
    }
}