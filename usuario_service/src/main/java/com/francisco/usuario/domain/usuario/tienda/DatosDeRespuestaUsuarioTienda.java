package com.francisco.usuario.domain.usuario.tienda;


import java.time.LocalDateTime;

public record DatosDeRespuestaUsuarioTienda(
        Long id,
        String nombre,
        String email,
        Long tiendaId,
        LocalDateTime fechaDeAsgnacion
) {
    public DatosDeRespuestaUsuarioTienda(UsuarioTienda tienda) {
        this(tienda.getId(), tienda.getNombre(), tienda.getEmail(), tienda.getTiendaId(), tienda.getFechaDeAsignacion());
    }
}
