package dev.andreia.reservahoteis.controller;


import dev.andreia.reservahoteis.model.Hotel;
import dev.andreia.reservahoteis.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    private HotelService service;

    @Autowired
    public HotelController(HotelService service) {
        this.service = service;
    }

    @Operation(summary = "Obtém detalhes de um hotél por ID")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hotel> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findById(id));
    }

    @Operation(summary = "Obtém detalhes de um hotél por cidade")
    @GetMapping("/cidade/{cidade}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Hotel>> findByCidade(@PathVariable String cidade){
        return ResponseEntity.status(200).body(service.findByCidade(cidade));
    }

    @Operation(summary = "Obtém a lista de todos os hotéis")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Hotel>> findAll(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @Operation(summary = "Cria um novo hotél")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Hotel> save(@RequestBody Hotel hotel){
        return ResponseEntity.status(201).body(service.save(hotel));
    }

    @Operation(summary = "Atualiza um hotél por ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hotel> update(@PathVariable Long id, @RequestBody Hotel hotel){
        return ResponseEntity.status(200).body(service.update(id, hotel));
    }

    @Operation(summary = "Exclui um hotél por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
