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
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;
}
