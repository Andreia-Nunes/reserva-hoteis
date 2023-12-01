package dev.andreia.reservahoteis.controller;

import dev.andreia.reservahoteis.model.Pagamento;
import dev.andreia.reservahoteis.model.dtos.PagamentoConsultaDto;
import dev.andreia.reservahoteis.model.dtos.PagamentoCriacaoDto;
import dev.andreia.reservahoteis.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    private PagamentoService service;

    @Autowired
    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @Operation(summary = "Obtém detalhes de um pagamento por ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PagamentoConsultaDto> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.findByIdDto(id));
    }

    @Operation(summary = "Obtém a lista de todos os pagamentos")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PagamentoConsultaDto>> findAll(){
        return ResponseEntity.status(200).body(service.findAll());
    }

    @Operation(summary = "Cria um novo pagamento")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pagamento> save(@RequestBody PagamentoCriacaoDto pagamentoCriacaoDto){
        return ResponseEntity.status(201).body(service.save(pagamentoCriacaoDto));
    }

    @Operation(summary = "Atualiza um pagamento por ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Pagamento> update(@PathVariable Long id, @RequestBody Pagamento pagamento){
        return ResponseEntity.status(200).body(service.update(id, pagamento));
    }
}

