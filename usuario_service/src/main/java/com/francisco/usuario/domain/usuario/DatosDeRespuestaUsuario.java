package com.francisco.usuario.domain.usuario;

import java.time.LocalDateTime;

public record DatosDeRespuestaUsuario(
        Long id,
        String nombre,
        String email,
        String telefono,
        String celular,
        ROLL roll,
        LocalDateTime fechaDeRegistro,
        LocalDateTime fechaDeModificacion
) {
    public DatosDeRespuestaUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(),
                usuario.getTelefono(), usuario.getCelular(), usuario.getRoll(), usuario.getFechaDeRegistro(),
                usuario.getFechaDeModificacion());
    }
}
