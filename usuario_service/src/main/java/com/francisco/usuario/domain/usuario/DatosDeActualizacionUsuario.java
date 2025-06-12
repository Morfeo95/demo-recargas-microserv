package com.francisco.usuario.domain.usuario;


public record DatosDeActualizacionUsuario(
        String nombre,
        String email,
        String password,
        String telefono,
        String celular,
        ROLL roll
) {
}
