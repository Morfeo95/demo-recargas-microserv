package com.francisco.usuario.domain.usuario;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "usuario")
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String celular;

    @Enumerated(EnumType.STRING)
    private ROLL roll;

    private Boolean activo;

    @CreationTimestamp
    private LocalDateTime fechaDeRegistro;

    @UpdateTimestamp
    private LocalDateTime fechaDeModificacion;

    public Usuario(){}

    public Usuario(@Valid DatosDeRegistroUsuario datos, String password) {
        this.nombre = datos.nombre();
        this.email = datos.email().toLowerCase();
        this.password = password;
        if (datos.telefono() != null && !datos.telefono().isEmpty()){
            this.telefono = datos.telefono();
        }
        this.celular = datos.celular();
        this.roll = datos.roll();
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public ROLL getRoll() {
        return roll;
    }

    public void setRoll(ROLL roll) {
        this.roll = roll;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaDeRegistro() {
        return fechaDeRegistro;
    }

    public LocalDateTime getFechaDeModificacion() {
        return fechaDeModificacion;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+this.roll.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void actualiza(DatosDeActualizacionUsuario datos) {
        if (datos.nombre() != null && !datos.nombre().isEmpty()) {
            this.nombre = datos.nombre();
        }
        if (datos.email() != null && !datos.email().isEmpty()) {
            this.email = datos.email().toLowerCase();
        }
        if (datos.telefono() != null && !datos.telefono().isEmpty()) {
            this.telefono = datos.telefono();
        }
        if (datos.celular() != null && !datos.celular().isEmpty()) {
            this.celular = datos.celular();
        }
        if (datos.roll() != null) {
            this.roll = datos.roll();
        }
    }

    public void setActivo() {
        this.activo = false;
    }
    public void setActivoTrue() { this.activo = true; }
}
