package dev.andreia.reservahoteis.service.impl;

import dev.andreia.reservahoteis.model.Hotel;
import dev.andreia.reservahoteis.model.Quarto;
import dev.andreia.reservahoteis.repository.HotelRepository;
import dev.andreia.reservahoteis.repository.QuartoRepository;
import dev.andreia.reservahoteis.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class QuartoServiceImpl implements ServiceInterface<Quarto> {

    private QuartoRepository quartoRepository;
    private HotelRepository hotelRepository;

    @Autowired
    public QuartoServiceImpl(QuartoRepository repository, HotelRepository hotelRepository) {
        this.quartoRepository = repository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Quarto findById(Long id) {
        return quartoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Quarto> findAll() {
        return quartoRepository.findAll();
    }

    @Transactional
    @Override
    public Quarto save(Quarto objectToSave) {
        Hotel hotel = this.buscaHotel(objectToSave.getHotel().getId());
        objectToSave.setHotel(hotel);

        return quartoRepository.save(objectToSave);
    }

    @Override
    public Quarto update(Long id, Quarto objectUpdated) {
        Quarto quartoBD = this.findById(id);

        quartoBD.setTipo(objectUpdated.getTipo());
        quartoBD.setDisponibilidades(objectUpdated.getDisponibilidades());
        quartoBD.setPrecoNoite(objectUpdated.getPrecoNoite());

        return quartoRepository.save(quartoBD);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Quarto quartoToDelete = this.findById(id);

        quartoRepository.delete(quartoToDelete);
        quartoRepository.flush();
    }

    private Hotel buscaHotel(Long id){
        return hotelRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("O hotel informado não existe. Por favor, cadastre-o antes do quarto."));
    }

    public Set<LocalDate> consultarDisponibilidades(Long id){
        Quarto quartoBD = this.findById(id);
        return quartoBD.getDisponibilidades();
    }
}
