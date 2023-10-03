package dev.andreia.reservahoteis.model.dtos;

import dev.andreia.reservahoteis.model.Endereco;
import dev.andreia.reservahoteis.model.Quarto;
import dev.andreia.reservahoteis.model.enums.TiposQuarto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record QuartoConsultaDto(
        Long id,
        BigDecimal precoPorNoite,
        TiposQuarto tipoDeQuarto,
        Long idHotel,
        String nomeHotel,
        Endereco endereco,
        Set<LocalDate> disponibilidades
) {

    public QuartoConsultaDto(Quarto quartoModel){
        this(
                quartoModel.getId(),
                quartoModel.getPrecoPorNoite(),
                quartoModel.getTipoDeQuarto(),
                quartoModel.getHotel().getId(),
                quartoModel.getHotel().getNome(),
                quartoModel.getHotel().getEndereco(),
                quartoModel.getDisponibilidades()
        );
    }
}
