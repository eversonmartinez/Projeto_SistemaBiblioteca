package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
public class Copia implements Serializable {
    @Id
    @SequenceGenerator(name="seq_copia", sequenceName = "seq_copia_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_copia", strategy = GenerationType.SEQUENCE)
    private Long id;

}
