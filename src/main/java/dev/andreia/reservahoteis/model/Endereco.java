package dev.andreia.reservahoteis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long id;

    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false, length = 45)
    private String bairro;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 45)
    private String cidade;

    @Column(nullable = false, length = 45)
    private String estado;

    @Column(nullable = false, length = 45)
    private String pais;
}
