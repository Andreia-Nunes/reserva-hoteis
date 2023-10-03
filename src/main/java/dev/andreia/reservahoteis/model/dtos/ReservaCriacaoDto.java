package dev.andreia.reservahoteis.model.dtos;

import dev.andreia.reservahoteis.model.Pagamento;

import java.time.LocalDate;
import java.util.List;

public record ReservaCriacaoDto(
        Long idCliente,
        Long idQuarto,
        LocalDate dataCheckIn,
        LocalDate dataCheckOut,
        List<Pagamento> pagamentos) {
}
