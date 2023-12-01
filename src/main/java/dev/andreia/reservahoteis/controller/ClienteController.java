package dev.andreia.reservahoteis.controller;


import dev.andreia.reservahoteis.model.Cliente;
import dev.andreia.reservahoteis.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService service;

    @Autowired
    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Obtém detalhes de um cliente por ID")
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findById(id));
    }

    @Operation(summary = "Obtém detalhes de um cliente por CPF")
    @GetMapping("/cpf/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> findByCpf(@PathVariable String cpf){
        return ResponseEntity.status(200).body(service.findByCpf(cpf));
    }

    @Operation(summary = "Obtém a lista de todos os clientes")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Cliente>> findAll(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @Operation(summary = "Cria um novo cliente")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        return ResponseEntity.status(201).body(service.save(cliente));
    }

    @Operation(summary = "Atualiza um cliente por ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente){
        return ResponseEntity.status(200).body(service.update(id, cliente));
    }

    @Operation(summary = "Exclui um cliente por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
