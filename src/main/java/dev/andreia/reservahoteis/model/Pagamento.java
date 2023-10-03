package dev.andreia.reservahoteis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.andreia.reservahoteis.model.enums.MeiosPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long id;

    @Column(nullable = false, columnDefinition = "decimal(8,2)")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 45)
    private MeiosPagamento meioDePagamento;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_reserva", nullable = false)
    @JsonBackReference
    private Reserva reserva;
}
