package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Leitor implements Serializable {
    @Id
    @SequenceGenerator(name="seq_leitor", sequenceName="seq_leitor_id", allocationSize = 1)
    @GeneratedValue(generator="seq_leitor", strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name="nome", length=50, nullable = false)
    @Setter
    private String nome;
    @Column(name="telefone", length=11, nullable = false)
    @Setter
    private String telefone;
    @Column(name = "email", length=50, nullable= false)
    @Setter
    private String email;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Usuario usuario;

    public Leitor(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leitor leitor = (Leitor) o;
        return Objects.equals(id, leitor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
