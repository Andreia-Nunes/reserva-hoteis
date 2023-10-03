package dev.andreia.reservahoteis.model.dtos;

import dev.andreia.reservahoteis.model.enums.TiposQuarto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record QuartoCriacaoDto(
        BigDecimal precoPorNoite,
        TiposQuarto tipoDeQuarto,
        Set<LocalDate> disponibilidades,
        Long idHotel
) {
}
