package dev.andreia.reservahoteis.controller.security;

import dev.andreia.reservahoteis.model.dtos.security.AuthenticationDto;
import dev.andreia.reservahoteis.model.dtos.security.LoginResponseDto;
import dev.andreia.reservahoteis.model.dtos.security.RegisterDto;
import dev.andreia.reservahoteis.model.security.User;
import dev.andreia.reservahoteis.service.security.TokenService;
import dev.andreia.reservahoteis.service.security.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final AuthorizationService authorizationService;

    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthorizationService authorizationService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Realiza o login de um usuário do sistema")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Operation(summary = "Cadastra um novo usuário")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity register(@RequestBody @Valid RegisterDto data){
        authorizationService.register(data);
        return ResponseEntity.ok().build();
    }
}
