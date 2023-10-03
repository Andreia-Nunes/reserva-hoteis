package dev.andreia.reservahoteis.service;

import dev.andreia.reservahoteis.model.Pagamento;
import dev.andreia.reservahoteis.model.dtos.PagamentoConsultaDto;
import dev.andreia.reservahoteis.repository.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    private PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public Pagamento findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public PagamentoConsultaDto findByIdDto(Long id) {
        return new PagamentoConsultaDto(this.findById(id));
    }

    public List<PagamentoConsultaDto> findAll() {
        List<Pagamento> pagamentosBD = repository.findAll();

        return pagamentosBD.stream()
                .map(pagamento -> new PagamentoConsultaDto(pagamento))
                .collect(Collectors.toList());
    }

    public Pagamento update(Long id, Pagamento pagamento) {
        Pagamento pagamentoBD = this.findById(id);

        pagamentoBD.setMeioDePagamento(pagamento.getMeioDePagamento());
        pagamentoBD.setValor(pagamento.getValor());

        return repository.save(pagamentoBD);
    }
}
