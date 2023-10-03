package dev.andreia.reservahoteis.service;

import dev.andreia.reservahoteis.model.Endereco;
import dev.andreia.reservahoteis.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnderecoService {

    private EnderecoRepository repository;

    @Autowired
    public EnderecoService(EnderecoRepository repository) {
        this.repository = repository;
    }

    public Endereco findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Endereco> findAll() {
        return repository.findAll();
    }

    public Endereco update(Long id, Endereco endereco) {
        Endereco enderecoBD = this.findById(id);

        enderecoBD.setLogradouro(endereco.getLogradouro());
        enderecoBD.setNumero(endereco.getNumero());
        enderecoBD.setBairro(endereco.getBairro());
        enderecoBD.setCep(endereco.getCep());
        enderecoBD.setCidade(endereco.getCidade());
        enderecoBD.setEstado(endereco.getEstado());
        enderecoBD.setPais(endereco.getPais());

        return repository.save(enderecoBD);
    }
}
