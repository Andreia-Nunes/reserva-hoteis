package dev.andreia.reservahoteis.service.impl;

import dev.andreia.reservahoteis.model.Endereco;
import dev.andreia.reservahoteis.repository.EnderecoRepository;
import dev.andreia.reservahoteis.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnderecoServiceImpl implements ServiceInterface<Endereco> {

    private EnderecoRepository repository;

    @Autowired
    public EnderecoServiceImpl(EnderecoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Endereco findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Endereco> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Endereco save(Endereco objectToSave) {
        return repository.save(objectToSave);
    }

    @Override
    public Endereco update(Long id, Endereco objectUpdated) {
        Endereco enderecoBD = this.findById(id);

        enderecoBD.setLogradouro(objectUpdated.getLogradouro());
        enderecoBD.setNumero(objectUpdated.getNumero());
        enderecoBD.setBairro(objectUpdated.getBairro());
        enderecoBD.setCep(objectUpdated.getCep());
        enderecoBD.setCidade(objectUpdated.getCidade());
        enderecoBD.setEstado(objectUpdated.getEstado());
        enderecoBD.setPais(objectUpdated.getPais());

        return repository.save(enderecoBD);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Endereco enderecoToDelete = this.findById(id);

        repository.delete(enderecoToDelete);
        repository.flush();
    }
}
