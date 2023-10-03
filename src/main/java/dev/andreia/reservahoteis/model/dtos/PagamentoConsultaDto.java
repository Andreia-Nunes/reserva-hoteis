package dev.andreia.reservahoteis.model.dtos;

import dev.andreia.reservahoteis.model.Pagamento;
import dev.andreia.reservahoteis.model.enums.MeiosPagamento;

import java.math.BigDecimal;

public record PagamentoConsultaDto(
        Long id,
        BigDecimal valor,
        MeiosPagamento meioDePagamento,
        Long idReserva
) {

    public  PagamentoConsultaDto(Pagamento pagamentoModel){
        this(
                pagamentoModel.getId(),
                pagamentoModel.getValor(),
                pagamentoModel.getMeioDePagamento(),
                pagamentoModel.getReserva().getId()
        );
    }
}
