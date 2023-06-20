package com.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Copia implements Serializable {
    @Id
    @SequenceGenerator(name="seq_copia", sequenceName = "seq_copia_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_copia", strategy = GenerationType.SEQUENCE)
    private Long id;

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
        return "Copia" +" id=" + id;
    }
}
