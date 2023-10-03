package dev.andreia.reservahoteis.service;

import dev.andreia.reservahoteis.model.Hotel;
import dev.andreia.reservahoteis.model.Quarto;
import dev.andreia.reservahoteis.model.dtos.QuartoConsultaDto;
import dev.andreia.reservahoteis.model.dtos.QuartoCriacaoDto;
import dev.andreia.reservahoteis.repository.HotelRepository;
import dev.andreia.reservahoteis.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuartoService {

    private QuartoRepository quartoRepository;
    private HotelRepository hotelRepository;

    @Autowired
    public QuartoService(QuartoRepository repository, HotelRepository hotelRepository) {
        this.quartoRepository = repository;
        this.hotelRepository = hotelRepository;
    }

    public Quarto findById(Long id) {
        return quartoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public QuartoConsultaDto findByIdDto(Long id) {
        return new QuartoConsultaDto(this.findById(id));
    }

    public List<QuartoConsultaDto> findAll() {
        List<Quarto> quartosBD = quartoRepository.findAll();
        return quartosBD.stream()
                .map(quartoBD -> new QuartoConsultaDto(quartoBD))
                .collect(Collectors.toList());
    }

    public List<QuartoConsultaDto> findQuartosDisponiveis(LocalDate data) {
        List<Quarto> quartosDisponiveisBD = quartoRepository.findQuartosDisponiveis(data);

        return quartosDisponiveisBD.stream()
                .map(quartoBD -> new QuartoConsultaDto(quartoBD))
                .collect(Collectors.toList());
    }

    @Transactional
    public Quarto save(QuartoCriacaoDto dto) {
        Hotel hotel = this.buscaHotel(dto.idHotel());

        Quarto quartoModel = new Quarto();
        quartoModel.setPrecoPorNoite(dto.precoPorNoite());
        quartoModel.setTipoDeQuarto(dto.tipoDeQuarto());
        quartoModel.setDisponibilidades(dto.disponibilidades());
        quartoModel.setHotel(hotel);

        return quartoRepository.save(quartoModel);
    }

    public Quarto update(Long id, Quarto quarto) {
        Quarto quartoBD = this.findById(id);

        quartoBD.setTipoDeQuarto(quarto.getTipoDeQuarto());
        quartoBD.setDisponibilidades(quarto.getDisponibilidades());
        quartoBD.setPrecoPorNoite(quarto.getPrecoPorNoite());

        return quartoRepository.save(quartoBD);
    }

    @Transactional
    public void delete(Long id) {
        Quarto quartoToDelete = this.findById(id);

        quartoRepository.delete(quartoToDelete);
        quartoRepository.flush();
    }

    private Hotel buscaHotel(Long id){
        return hotelRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("O hotel informado não existe. Por favor, cadastre-o antes do quarto."));
    }

    public Set<LocalDate> checkAvailability(Long id){
        Quarto quartoBD = this.findById(id);
        return quartoBD.getDisponibilidades();
    }

}
