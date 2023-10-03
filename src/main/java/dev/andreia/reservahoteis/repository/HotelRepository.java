package dev.andreia.reservahoteis.repository;

import dev.andreia.reservahoteis.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByEnderecoCidade(String cidade);
}
