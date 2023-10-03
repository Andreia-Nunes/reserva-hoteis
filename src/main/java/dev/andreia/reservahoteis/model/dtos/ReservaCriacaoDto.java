package dev.andreia.reservahoteis.model.dtos;

import java.time.LocalDate;

public record ReservaCriacaoDto(
        Long idCliente,
        Long idQuarto,
        LocalDate dataCheckIn,
        LocalDate dataCheckOut) {
}
