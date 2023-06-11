package model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
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
    @ManyToOne
    @JoinColumn(name = "autor", referencedColumnName = "id", nullable = false)
    private Autor autor;
}
