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

    @Column(nullable = false, columnDefinition = "decimal(8,2)")
    private BigDecimal precoNoite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private TiposQuarto tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;

    @ElementCollection(targetClass = LocalDate.class)
    @CollectionTable(name = "Disponibilidades")
    @Column(name = "data", nullable = false)
    private Set<LocalDate> disponibilidades = new HashSet<>();
}
