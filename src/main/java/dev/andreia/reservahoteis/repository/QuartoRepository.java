package dev.andreia.reservahoteis.repository;

import dev.andreia.reservahoteis.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    @Query("SELECT q FROM Quarto q WHERE ?1 MEMBER OF q.disponibilidades")
    List<Quarto> findQuartosDisponiveis(LocalDate data);
}
