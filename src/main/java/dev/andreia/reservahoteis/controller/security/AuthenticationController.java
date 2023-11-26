package dev.andreia.reservahoteis.controller.security;

import dev.andreia.reservahoteis.model.dtos.security.AuthenticationDto;
import dev.andreia.reservahoteis.model.dtos.security.RegisterDto;
import dev.andreia.reservahoteis.service.security.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final AuthorizationService authorizationService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthorizationService authorizationService) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data){
        authorizationService.register(data);
        return ResponseEntity.ok().build();
    }
}
