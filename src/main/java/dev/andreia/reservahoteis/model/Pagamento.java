package dev.andreia.reservahoteis.model;

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
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private MeiosPagamento meioDePagamento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;
}
