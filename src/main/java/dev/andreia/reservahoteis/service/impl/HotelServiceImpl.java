package dev.andreia.reservahoteis.service.impl;

import dev.andreia.reservahoteis.model.Hotel;
import dev.andreia.reservahoteis.repository.HotelRepository;
import dev.andreia.reservahoteis.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HotelServiceImpl implements ServiceInterface<Hotel> {

    private HotelRepository repository;

    @Autowired
    public HotelServiceImpl(HotelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Hotel findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Hotel> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Hotel save(Hotel objectToSave) {
        return repository.save(objectToSave);
    }

    @Override
    public Hotel update(Long id, Hotel objectUpdated) {
        Hotel hotelBD = this.findById(id);

        hotelBD.setNome(objectUpdated.getNome());
        hotelBD.setClassificacao(objectUpdated.getClassificacao());
        hotelBD.setDescricao(objectUpdated.getDescricao());
        hotelBD.setEndereco(objectUpdated.getEndereco());
        hotelBD.setComodidades(objectUpdated.getComodidades());

        return repository.save(hotelBD);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Hotel hotelToDelete = this.findById(id);

        repository.delete(hotelToDelete);
        repository.flush();
    }
}
