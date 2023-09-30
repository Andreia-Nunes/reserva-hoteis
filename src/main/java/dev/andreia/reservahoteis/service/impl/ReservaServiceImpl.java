package dev.andreia.reservahoteis.service.impl;

import dev.andreia.reservahoteis.model.Cliente;
import dev.andreia.reservahoteis.model.Quarto;
import dev.andreia.reservahoteis.model.Reserva;
import dev.andreia.reservahoteis.model.enums.StatusReserva;
import dev.andreia.reservahoteis.repository.ClienteRepository;
import dev.andreia.reservahoteis.repository.QuartoRepository;
import dev.andreia.reservahoteis.repository.ReservaRepository;
import dev.andreia.reservahoteis.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservaServiceImpl implements ServiceInterface<Reserva> {

    private ReservaRepository reservaRepository;
    private ClienteRepository clienteRepository;
    private QuartoRepository quartoRepository;

    @Autowired
    public ReservaServiceImpl(ReservaRepository repository, ClienteRepository clienteRepository, QuartoRepository quartoRepository) {
        this.reservaRepository = repository;
        this.clienteRepository = clienteRepository;
        this.quartoRepository = quartoRepository;
    }

    @Override
    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    @Override
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Transactional
    @Override
    public Reserva save(Reserva objectToSave) {
        Cliente cliente = buscaCliente(objectToSave.getCliente().getId());
        Quarto quarto = buscaQuarto(objectToSave.getQuarto().getId());

        //Verifica se a reserva possui pelo menos 1 dia.
        verificaTempoReserva(objectToSave.getDataCheckIn(), objectToSave.getDataCheckOut());

        //Verifica se o quarto está disponível entre as datas solicitadas.
        verificaDisponibilidadeQuarto(quarto, objectToSave.getDataCheckIn(), objectToSave.getDataCheckOut());

        //Altera as datas disponíveis, excluindo aquelas que fazem parte da reserva.
        quarto.alterarDisponibilidade(objectToSave.getDataCheckIn(), objectToSave.getDataCheckOut(), true);

        objectToSave.setCliente(cliente);
        objectToSave.setQuarto(quarto);

        return reservaRepository.save(objectToSave);
    }

    @Override
    public Reserva update(Long id, Reserva objectUpdated) {
        Reserva reservaBD = this.findById(id);

        //Se for cancelamento de reserva, libera as datas de disponibilidade do quarto.
        if(objectUpdated.getStatusReserva() == StatusReserva.CANCELADA){
            Quarto quarto = reservaBD.getQuarto();
            quarto.alterarDisponibilidade(reservaBD.getDataCheckIn(), reservaBD.getDataCheckOut(), false);
        }

        reservaBD.setStatusReserva(objectUpdated.getStatusReserva());
        reservaBD.setPagamentos(objectUpdated.getPagamentos());

        return reservaRepository.save(reservaBD);
    }

    @Override
    public void delete(Long id) {
        Reserva reservaToDelete = this.findById(id);

        reservaRepository.delete(reservaToDelete);
        reservaRepository.flush();
    }

    private Cliente buscaCliente(Long clienteId){
        return clienteRepository.findById(clienteId).orElseThrow(() ->
                new IllegalArgumentException("O cliente informado não existe. Por favor, cadastre-o antes da reserva."));
    }

    private Quarto buscaQuarto(Long quartoId){
        return quartoRepository.findById(quartoId).orElseThrow(() ->
                new IllegalArgumentException("O quarto informado não existe. Por favor, cadastre-o antes da reserva."));
    }

    private void verificaTempoReserva(LocalDate dataCheckIn, LocalDate dataCheckOut){
        if(dataCheckIn.equals(dataCheckOut)){
            throw new IllegalArgumentException("O tempo mínimo de reserva é 1 dia.");
        }
    }

    private void verificaDisponibilidadeQuarto(Quarto quarto, LocalDate dataCheckIn, LocalDate dataCheckOut){
        //Cria lista com todas as datas inclusas na reserva.
        List<LocalDate> datasDaReserva = new ArrayList<>();
        LocalDate dataAtual = dataCheckIn;

        while (!dataAtual.isAfter(dataCheckOut)) {
            datasDaReserva.add(dataAtual);
            dataAtual = dataAtual.plusDays(1);
        }

        boolean quartoDisponivel = quarto.estaDisponivel(datasDaReserva);

        if(!quartoDisponivel){
            throw new IllegalArgumentException(String.format(
                    "O quarto solicitado não está disponível entre as datas %s e %s. Por favor, consultar disponibilidades.",
                    dataCheckIn,
                    dataCheckOut));
        }
    }
}
