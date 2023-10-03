package dev.andreia.reservahoteis.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer classificacao;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Comodidades.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Comodidades")
    @Column(name = "comodidade", nullable = false, length = 45)
    private Set<Comodidades> comodidades = new HashSet<>();

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    private List<Quarto> quartos = new ArrayList<>();
}
