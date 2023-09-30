package dev.andreia.reservahoteis.service.impl;

import dev.andreia.reservahoteis.model.Pagamento;
import dev.andreia.reservahoteis.repository.PagamentoRepository;
import dev.andreia.reservahoteis.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PagamentoServiceImpl implements ServiceInterface<Pagamento> {

    private PagamentoRepository repository;

    @Autowired
    public PagamentoServiceImpl(PagamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pagamento findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Pagamento> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Pagamento save(Pagamento objectToSave) {
        return repository.save(objectToSave);
    }

    @Override
    public Pagamento update(Long id, Pagamento objectUpdated) {
        Pagamento pagamentoBD = this.findById(id);

        pagamentoBD.setMeioDePagamento(objectUpdated.getMeioDePagamento());
        pagamentoBD.setValor(objectUpdated.getValor());

        return repository.save(pagamentoBD);
    }

    @Override
    public void delete(Long id) {
        Pagamento pagamentoToDelete = this.findById(id);

        repository.delete(pagamentoToDelete);
        repository.flush();
    }
}
