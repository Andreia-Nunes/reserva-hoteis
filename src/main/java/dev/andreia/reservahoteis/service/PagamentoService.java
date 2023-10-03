package dev.andreia.reservahoteis.service;

import dev.andreia.reservahoteis.model.Pagamento;
import dev.andreia.reservahoteis.model.Reserva;
import dev.andreia.reservahoteis.model.dtos.PagamentoConsultaDto;
import dev.andreia.reservahoteis.model.dtos.PagamentoCriacaoDto;
import dev.andreia.reservahoteis.repository.PagamentoRepository;
import dev.andreia.reservahoteis.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    private PagamentoRepository pagamentoRepository;
    private ReservaRepository reservaRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository, ReservaRepository reservaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.reservaRepository = reservaRepository;
    }

    public Pagamento findById(Long id) {
        return pagamentoRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public PagamentoConsultaDto findByIdDto(Long id) {
        return new PagamentoConsultaDto(this.findById(id));
    }

    public List<PagamentoConsultaDto> findAll() {
        List<Pagamento> pagamentosBD = pagamentoRepository.findAll();

        return pagamentosBD.stream()
                .map(pagamento -> new PagamentoConsultaDto(pagamento))
                .collect(Collectors.toList());
    }

    @Transactional
    public Pagamento save(PagamentoCriacaoDto pagamentoCriacaoDto){
        Reserva reserva = reservaRepository.findById(pagamentoCriacaoDto.idReserva()).orElseThrow(() ->
                new IllegalArgumentException("A reserva informada não existe. Por favor, cadastre-a antes do pagamento."));

        Pagamento pagamentoModel = new Pagamento();

        pagamentoModel.setValor(pagamentoCriacaoDto.valor());
        pagamentoModel.setMeioDePagamento(pagamentoCriacaoDto.meioDePagamento());
        pagamentoModel.setReserva(reserva);

        reserva.getPagamentos().add(pagamentoModel);

        return pagamentoRepository.save(pagamentoModel);
    }

    public Pagamento update(Long id, Pagamento pagamento) {
        Pagamento pagamentoBD = this.findById(id);

        pagamentoBD.setMeioDePagamento(pagamento.getMeioDePagamento());
        pagamentoBD.setValor(pagamento.getValor());

        return pagamentoRepository.save(pagamentoBD);
    }
}
