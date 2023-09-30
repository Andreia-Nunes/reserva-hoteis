package dev.andreia.reservahoteis.model;

import dev.andreia.reservahoteis.model.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private BigDecimal precoTotal;

    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
            name = "reserva_quartos",
            joinColumns = @JoinColumn(name = "id_reserva"),
            inverseJoinColumns = @JoinColumn(name = "id_quarto")
    )
    private List<Quarto> quartos = new ArrayList<>();

    @OneToMany(mappedBy = "reserva", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();
}
