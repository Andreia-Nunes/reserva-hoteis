package dev.andreia.reservahoteis.controller;


import dev.andreia.reservahoteis.model.Quarto;
import dev.andreia.reservahoteis.model.dtos.QuartoConsultaDto;
import dev.andreia.reservahoteis.model.dtos.QuartoCriacaoDto;
import dev.andreia.reservahoteis.service.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/quarto")
public class QuartoController {

    private QuartoService service;

    @Autowired
    public QuartoController(QuartoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtém detalhes de um quarto por ID ")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuartoConsultaDto> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findByIdDto(id));
    }

    @Operation(summary = "Obtém a lista de todos os quartos")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuartoConsultaDto>> findAll(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @Operation(summary = "Obtém a lista de todos os quartos disponíveis por data")
    @GetMapping("/disponiveis")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuartoConsultaDto>> findQuartosDisponiveis(@RequestParam LocalDate data){
        return ResponseEntity.status(200).body(service.findQuartosDisponiveis(data));
    }

    @Operation(summary = "Cria um novo quarto")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Quarto> save(@RequestBody QuartoCriacaoDto quartoCriacaoDto){
        return ResponseEntity.status(201).body(service.save(quartoCriacaoDto));
    }

    @Operation(summary = "Atualiza um quarto por ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Quarto> update(@PathVariable Long id, @RequestBody Quarto quarto){
        return ResponseEntity.status(200).body(service.update(id, quarto));
    }

    @Operation(summary = "Exclui um quarto por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtém todas as datas disponíveis de um quarto")
    @GetMapping("/disponibilidade/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<LocalDate>> checkAvailability(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.checkAvailability(id));
    }
}

