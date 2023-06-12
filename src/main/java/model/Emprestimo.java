package model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Emprestimo implements Serializable {
    private Long id;
    private LocalDate data;
    private LocalDate dataPrevistaEntrega;
    private LocalDate dataEntrega;

}
