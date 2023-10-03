package dev.andreia.reservahoteis.service;

import dev.andreia.reservahoteis.model.Cliente;
import dev.andreia.reservahoteis.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteService {

    private ClienteRepository repository;

    @Autowired
    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        this.verificaCpf(cliente.getCpf());

        return repository.save(cliente);
    }

    public Cliente update(Long id, Cliente cliente) {
        Cliente clienteBD = this.findById(id);

        clienteBD.setNome(cliente.getNome());
        clienteBD.setEmail(cliente.getEmail());
        clienteBD.setTelefone(cliente.getTelefone());

        return repository.save(clienteBD);
    }

    @Transactional
    public void delete(Long id) {
        Cliente clienteToDelete = this.findById(id);

        repository.delete(clienteToDelete);
        repository.flush();
    }

    private void verificaCpf(String cpf){
        if(repository.existsByCpf(cpf)){
            throw new IllegalArgumentException("Já existe cliente com o CPF informado.");
        }
    }
}
