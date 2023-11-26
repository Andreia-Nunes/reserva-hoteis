package dev.andreia.reservahoteis.model.dtos.security;

import dev.andreia.reservahoteis.model.enums.security.UserRole;

public record RegisterDto(
        String login,
        String password,
        UserRole role
) {
}
