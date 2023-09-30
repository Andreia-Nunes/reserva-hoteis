package dev.andreia.reservahoteis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDate nascimento;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
}
