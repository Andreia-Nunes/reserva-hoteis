package dev.andreia.reservahoteis.controller;

import dev.andreia.reservahoteis.model.Endereco;
import dev.andreia.reservahoteis.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private EnderecoService service;

    @Autowired
    public EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtém detalhes de um endereco por ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Endereco> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findById(id));
    }

    @Operation(summary = "Obtém a lista de todos os enderecos")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Endereco>> findAll(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @Operation(summary = "Atualiza um endereço por ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody Endereco endereco){
        return ResponseEntity.status(200).body(service.update(id, endereco));
    }
}
