package dev.andreia.reservahoteis.model.dtos.security;

public record AuthenticationDto(
        String login,
        String password
) {
}
