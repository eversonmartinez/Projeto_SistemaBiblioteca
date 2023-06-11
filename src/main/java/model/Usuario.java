package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Usuario implements Serializable {
    @Id
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario_id", allocationSize = 1)
    @GeneratedValue(generator="seq_usuario", strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "login", length = 10, nullable = false)
    private String login;
    @Column(name= "senha", length = 15, nullable = false)
    private String senha;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }
}
