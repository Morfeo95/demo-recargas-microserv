package com.francisco.usuario.domain.usuario;


import jakarta.validation.constraints.NotNull;

public record DatosDeRegistroUsuario(

        @NotNull
        String nombre,

        @NotNull
        String email,

        @NotNull
        String password,

        String telefono,

        @NotNull
        String celular,

        @NotNull
        ROLL roll
) {
}
