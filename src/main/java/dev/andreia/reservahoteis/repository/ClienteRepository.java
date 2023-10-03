package dev.andreia.reservahoteis.repository;

import dev.andreia.reservahoteis.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCpf(String cpf);

    Optional<Cliente> findClienteByCpf(String cpf);
}
