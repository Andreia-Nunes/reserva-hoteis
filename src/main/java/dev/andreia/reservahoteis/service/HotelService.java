package dev.andreia.reservahoteis.service;

import dev.andreia.reservahoteis.model.Hotel;
import dev.andreia.reservahoteis.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HotelService {

    private HotelRepository repository;

    @Autowired
    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public Hotel findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }


    public List<Hotel> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Hotel save(Hotel hotel) {
        hotel.getQuartos().stream().forEach((quarto -> quarto.setHotel(hotel)));
        return repository.save(hotel);
    }

    public Hotel update(Long id, Hotel hotel) {
        Hotel hotelBD = this.findById(id);

        hotelBD.setNome(hotel.getNome());
        hotelBD.setClassificacao(hotel.getClassificacao());
        hotelBD.setDescricao(hotel.getDescricao());
        hotelBD.setComodidades(hotel.getComodidades());

        return repository.save(hotelBD);
    }

    @Transactional
    public void delete(Long id) {
        Hotel hotelToDelete = this.findById(id);

        repository.delete(hotelToDelete);
        repository.flush();
    }
}
