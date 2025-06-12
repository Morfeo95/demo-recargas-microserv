package com.francisco.usuario.domain.usuario.tienda;

import com.francisco.usuario.domain.usuario.DatosDeRegistroUsuario;
import com.francisco.usuario.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "usuario_tienda")
@Table(name = "usuario_tienda")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class UsuarioTienda extends Usuario {

    @ManyToOne
    @JoinColumn(name = "promotor_id", nullable = false)
    private Usuario promotor;

    private Long tiendaId;
    @CreationTimestamp
    private LocalDateTime fechaDeAsignacion;

    public  UsuarioTienda(){}

    public UsuarioTienda(@Valid DatosDeRegistroUsuario datos, Long tiendaId, String passwordEncriptado,
                         Usuario usuario) {
        this.promotor = usuario;
        this.tiendaId = tiendaId;
        setNombre(datos.nombre());
        setEmail(datos.email());
        setPassword(passwordEncriptado);
        if (!datos.telefono().isEmpty() && datos.telefono() != null){
            setTelefono(datos.telefono());
        }
        setCelular(datos.celular());
        setRoll(datos.roll());
        setActivoTrue();
    }

    public Usuario getPromotor() {
        return promotor;
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public LocalDateTime getFechaDeAsignacion() {
        return fechaDeAsignacion;
    }

    public void setPromotor(Usuario promotor) {
        this.promotor = promotor;
    }
}
