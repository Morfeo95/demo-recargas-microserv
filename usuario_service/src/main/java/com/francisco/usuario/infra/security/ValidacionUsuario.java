package com.francisco.usuario.infra.security;

import com.francisco.usuario.domain.usuario.ROLL;
import com.francisco.usuario.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionUsuario {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    public boolean validaUsuarioAdmin(String token) {
        var id = tokenService.getClaim(token.replace("Bearer ", ""));
        var usuarioOptional = repository.findById(id);
        if (usuarioOptional.isEmpty()){
            return false;
        }
        var usuario = usuarioOptional.get();
        if (usuario.getRoll() != ROLL.ADMIN){
            return false;
        }
        var emailUsuarioAutenticado = tokenService.getSubject(token.replace("Bearer ", ""));
        return usuario.getEmail().equals(emailUsuarioAutenticado);
    }

    public boolean validaUsuarioPromotor(String token) {
        var id = tokenService.getClaim(token.replace("Bearer ", ""));
        var usuarioOptional = repository.findById(id);
        if (usuarioOptional.isEmpty()){
            return false;
        }
        var usuario = usuarioOptional.get();
        if (usuario.getRoll() != ROLL.PROMOTOR){
            return false;
        }
        var emailUsuarioAutenticado = tokenService.getSubject(token.replace("Bearer ", ""));
        return usuario.getEmail().equals(emailUsuarioAutenticado);
    }

    public boolean validaUsuarioTienda(String token) {
        var id = tokenService.getClaim(token.replace("Bearer ", ""));
        var usuarioOptional = repository.findById(id);
        if (usuarioOptional.isEmpty()){
            return false;
        }
        var usuario = usuarioOptional.get();
        if (usuario.getRoll() != ROLL.TIENDA){
            return false;
        }
        var emailUsuarioAutenticado = tokenService.getSubject(token.replace("Bearer ", ""));
        return usuario.getEmail().equals(emailUsuarioAutenticado);
    }
}
