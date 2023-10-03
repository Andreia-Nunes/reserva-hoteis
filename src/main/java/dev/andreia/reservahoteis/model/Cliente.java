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
@NamedQuery(name = "Cliente.findClienteByCpf"
        ,query = "select c from Cliente c where cpf = ?1")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 100)
    private String email;

    @Column(nullable = false, length = 14)
    private String telefone;

    @Column(nullable = false)
    private LocalDate nascimento;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) //CascadeType.MERGE
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;
}
