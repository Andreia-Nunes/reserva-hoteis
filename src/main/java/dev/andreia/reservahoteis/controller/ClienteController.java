package dev.andreia.reservahoteis.controller;


import dev.andreia.reservahoteis.model.Cliente;
import dev.andreia.reservahoteis.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findById(id));
    }

    @GetMapping("/cpf/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> findByCpf(@PathVariable String cpf){
        return ResponseEntity.status(200).body(service.findByCpf(cpf));
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Cliente>> findAll(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        return ResponseEntity.status(201).body(service.save(cliente));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente cliente){
        return ResponseEntity.status(200).body(service.update(id, cliente));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
