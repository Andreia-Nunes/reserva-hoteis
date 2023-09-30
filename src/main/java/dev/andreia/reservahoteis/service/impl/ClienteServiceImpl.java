package dev.andreia.reservahoteis.service.impl;

import dev.andreia.reservahoteis.model.Cliente;
import dev.andreia.reservahoteis.repository.ClienteRepository;
import dev.andreia.reservahoteis.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClienteServiceImpl implements ServiceInterface<Cliente> {

    private ClienteRepository repository;

    @Autowired
    public ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cliente findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Cliente save(Cliente objectToSave) {
        this.verificaCpf(objectToSave.getCpf());

        return repository.save(objectToSave);
    }

    @Override
    public Cliente update(Long id, Cliente objectUpdated) {
        Cliente clienteBD = this.findById(id);

        clienteBD.setNome(objectUpdated.getNome());
        clienteBD.setEmail(objectUpdated.getEmail());
        clienteBD.setEndereco(objectUpdated.getEndereco());
        clienteBD.setTelefone(objectUpdated.getTelefone());

        return repository.save(clienteBD);
    }

    @Transactional
    @Override
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
