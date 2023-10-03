package dev.andreia.reservahoteis.controller;


import dev.andreia.reservahoteis.model.Reserva;
import dev.andreia.reservahoteis.model.dtos.ReservaCriacaoDto;
import dev.andreia.reservahoteis.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    private ReservaService service;

    @Autowired
    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Reserva> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findById(id));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Reserva>> findAll(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reserva> save(@RequestBody ReservaCriacaoDto reservaCriacaoDto){
        return ResponseEntity.status(201).body(service.save(reservaCriacaoDto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Reserva> update(@PathVariable Long id, @RequestBody Reserva reserva){
        return ResponseEntity.status(200).body(service.update(id, reserva));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

