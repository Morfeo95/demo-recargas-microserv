package com.francisco.usuario.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosDeAuteticacionUsuario(
        @NotNull
        String email,
        @NotNull
        String password
) {
}
