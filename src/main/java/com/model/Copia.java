package com.model;

import jakarta.persistence.*;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Entity
public class Copia implements Serializable {
    @Id
    @SequenceGenerator(name="seq_copia", sequenceName = "seq_copia_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_copia", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @Column(name = "emprestavel")
    private Boolean emprestavel;

    public Copia(){
        emprestavel = false;
    }

    public Copia(Boolean emprestavel){
        this.emprestavel = emprestavel;
    }

    public Boolean getEmprestavel() {
        return emprestavel;
    }

    public void setEmprestavel(Boolean emprestavel) {
        this.emprestavel = emprestavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Copia copia = (Copia) o;
        return Objects.equals(id, copia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {

        String retorno = ("Copia" +" id=" + id);
        if(emprestavel)
            retorno+= " / Emprestável";
        else
            retorno+= " / Não Emprestável";
        return retorno;
    }
}
