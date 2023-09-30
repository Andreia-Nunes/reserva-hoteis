package dev.andreia.reservahoteis.model;

import dev.andreia.reservahoteis.model.enums.Comodidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hotel")
    private Long id;
    private String nome;
    private String descricao;
    private Integer classificacao;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Comodidades.class)
    @CollectionTable(name = "Comodidades")
    @Column(name = "comodidade")
    private Set<Comodidades> comodidades = new HashSet<>();

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quarto> quartos = new ArrayList<>();
}
