package dev.andreia.reservahoteis.model;

import dev.andreia.reservahoteis.model.enums.TiposQuarto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_quarto")
    private Long id;
    private BigDecimal precoNoite;

    @Enumerated(EnumType.STRING)
    private TiposQuarto tipoDeQuarto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hotel")
    private Hotel hotel;

    @ElementCollection(targetClass = LocalDate.class)
    @CollectionTable(name = "Disponibilidades")
    @Column(name = "data")
    private Set<LocalDate> disponibilidades = new HashSet<>();
}
