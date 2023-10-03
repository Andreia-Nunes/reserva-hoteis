package dev.andreia.reservahoteis.model.dtos;

import dev.andreia.reservahoteis.model.enums.MeiosPagamento;

import java.math.BigDecimal;

public record PagamentoCriacaoDto(
    BigDecimal valor,
    MeiosPagamento meioDePagamento,
    Long idReserva
) {
}
