package dev.andreia.reservahoteis.service;

import dev.andreia.reservahoteis.model.Cliente;
import dev.andreia.reservahoteis.model.Pagamento;
import dev.andreia.reservahoteis.model.Quarto;
import dev.andreia.reservahoteis.model.Reserva;
import dev.andreia.reservahoteis.model.dtos.ReservaCriacaoDto;
import dev.andreia.reservahoteis.model.enums.StatusReserva;
import dev.andreia.reservahoteis.repository.ClienteRepository;
import dev.andreia.reservahoteis.repository.QuartoRepository;
import dev.andreia.reservahoteis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservaService {

    private ReservaRepository reservaRepository;
    private ClienteRepository clienteRepository;
    private QuartoRepository quartoRepository;

    @Autowired
    public ReservaService(ReservaRepository repository, ClienteRepository clienteRepository, QuartoRepository quartoRepository) {
        this.reservaRepository = repository;
        this.clienteRepository = clienteRepository;
        this.quartoRepository = quartoRepository;
    }

    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Transactional
    public Reserva save(ReservaCriacaoDto reservaCriacaoDto) {
        Cliente cliente = buscarCliente(reservaCriacaoDto.idCliente());
        Quarto quarto = buscarQuarto(reservaCriacaoDto.idQuarto());
        LocalDate dataCheckIn = reservaCriacaoDto.dataCheckIn();
        LocalDate dataCheckOut = reservaCriacaoDto.dataCheckOut();

        //Verifica se a reserva possui pelo menos 1 dia.
        if(dataCheckIn.isEqual(dataCheckOut)){
            throw new IllegalArgumentException("O tempo mínimo de reserva é 1 dia.");
        }

        //Verifica se a data de check-in é anterior à de check-out.
        if(!dataCheckIn.isBefore(dataCheckOut)){
            throw new IllegalArgumentException("A data de check-in deve ser anterior à de check-out.");
        }

        //Verifica se o quarto está disponível entre as datas solicitadas.
        verificarDisponibilidade(quarto, dataCheckIn, dataCheckOut);

        //Altera as datas disponíveis, excluindo aquelas que fazem parte da reserva.
        quarto.alterarDisponibilidade(dataCheckIn, dataCheckOut, true);

        //Calcula preço total
        BigDecimal precoTotal = this.calcularPrecoTotal(this.calcularTempoDaReserva(dataCheckIn, dataCheckOut), quarto.getPrecoPorNoite());

        Reserva reservaModel = new Reserva();
        reservaModel.setCliente(cliente);
        reservaModel.setQuarto(quarto);
        reservaModel.setDataCheckIn(dataCheckIn);
        reservaModel.setDataCheckOut(dataCheckOut);
        reservaModel.setPrecoTotal(precoTotal);
        reservaModel.setStatusReserva(StatusReserva.CONFIRMADA);

        return reservaRepository.save(reservaModel);
    }

    public Reserva update(Long id, Reserva reserva) {
        Reserva reservaBD = this.findById(id);

        //Se for cancelamento de reserva, libera as datas de disponibilidade do quarto.
        if(reserva.getStatusReserva() == StatusReserva.CANCELADA){
            Quarto quarto = reservaBD.getQuarto();
            quarto.alterarDisponibilidade(reservaBD.getDataCheckIn(), reservaBD.getDataCheckOut(), false);
        }

        reservaBD.setStatusReserva(reserva.getStatusReserva());
        reservaBD.setPagamentos(reserva.getPagamentos());

        return reservaRepository.save(reservaBD);
    }

    public void delete(Long id) {
        Reserva reservaToDelete = this.findById(id);

        reservaRepository.delete(reservaToDelete);
        reservaRepository.flush();
    }

    private Cliente buscarCliente(Long clienteId){
        return clienteRepository.findById(clienteId).orElseThrow(() ->
                new IllegalArgumentException("O cliente informado não existe. Por favor, cadastre-o antes da reserva."));
    }

    private Quarto buscarQuarto(Long quartoId){
        return quartoRepository.findById(quartoId).orElseThrow(() ->
                new IllegalArgumentException("O quarto informado não existe. Por favor, cadastre-o antes da reserva."));
    }

    private int calcularTempoDaReserva(LocalDate dataCheckIn, LocalDate dataCheckOut){
        return Period.between(dataCheckIn, dataCheckOut).getDays();
    }

    private void verificarDisponibilidade(Quarto quarto, LocalDate dataCheckIn, LocalDate dataCheckOut){
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

    private BigDecimal calcularPrecoTotal(int tempoDaReserva, BigDecimal precoPorNoite) {
        return precoPorNoite.multiply(BigDecimal.valueOf(tempoDaReserva));
    }
}
