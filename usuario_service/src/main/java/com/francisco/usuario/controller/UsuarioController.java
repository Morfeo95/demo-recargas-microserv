package com.francisco.usuario.controller;

import com.francisco.usuario.domain.usuario.*;
import com.francisco.usuario.domain.usuario.tienda.DatosDeRespuestaUsuarioTienda;
import com.francisco.usuario.domain.usuario.tienda.UsuarioTienda;
import com.francisco.usuario.domain.usuario.tienda.UsuarioTiendaRepository;
import com.francisco.usuario.infra.security.TokenService;
import com.francisco.usuario.infra.security.ValidacionUsuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioTiendaRepository tiendaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ValidacionUsuario validacion;

    @Autowired
    TokenService tokenService;

    @PostMapping("/registrar-promotor")
    public ResponseEntity<DatosDeRespuestaUsuario> registrarUsuario(@Valid @RequestBody DatosDeRegistroUsuario datos,
                                                                    @RequestHeader("Authorization") String token){
        if (!validacion.validaUsuarioAdmin(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var password = encoder.encode(datos.password());
        var usuario = new Usuario(datos, password);
        repository.save(usuario);
        var datosDeRetorno = new DatosDeRespuestaUsuario(usuario);
        return  ResponseEntity.ok(datosDeRetorno);
    }

    @PostMapping("/registrar-tienda")
    public  ResponseEntity<DatosDeRespuestaUsuarioTienda> registrausuarioTienda(@Valid @RequestBody DatosDeRegistroUsuario datos,
                                                                                @RequestHeader("Authorization") String token,
                                                                                @RequestParam("tiendaId") Long tiendaId){
        if (!validacion.validaUsuarioPromotor(token) && !validacion.validaUsuarioAdmin(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var usuario = repository.getReferenceById(tokenService.getClaim(token.replace("Bearer ", "")));
        var passwordEncriptado = encoder.encode(datos.password());
        var tienda = new UsuarioTienda(datos, tiendaId, passwordEncriptado, usuario);
        repository.save(tienda);
        var datosDeRespuesta = new DatosDeRespuestaUsuarioTienda(tienda);
        return ResponseEntity.ok(datosDeRespuesta);
    }

    @PutMapping("/actualizar-promotor/{id}")
    @Transactional
    public ResponseEntity<DatosDeRespuestaUsuario> actualizaAsesores(@PathVariable Long id, @RequestBody DatosDeActualizacionUsuario datos,
                                                                     @RequestHeader("Authorization") String token){
        if (!validacion.validaUsuarioAdmin(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var usuarioOptinal = repository.findById(id);
        if (usuarioOptinal.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var usuario = usuarioOptinal.get();
        if (!datos.password().isEmpty() && datos.password() != null){
            var pass = encoder.encode(datos.password());
            usuario.setPassword(pass);
        }
        usuario.actualiza(datos);
        var datosRespuesta = new DatosDeRespuestaUsuario(usuario);
        return ResponseEntity.ok(datosRespuesta);
    }

    @PutMapping("/actualizar-tienda/{id}")
    @Transactional
    public ResponseEntity<DatosDeRespuestaUsuario> actualizaTiendas(@PathVariable Long id, @RequestBody DatosDeActualizacionUsuario datos,
                                                                    @RequestHeader("Authorization") String token,
                                                                    @RequestParam("promotorId") Long promotorId){
        if (!validacion.validaUsuarioAdmin(token) && validacion.validaUsuarioPromotor(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var usuarioOptinal = tiendaRepository.findById(id);
        if (usuarioOptinal.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var usuario = usuarioOptinal.get();
        if (!datos.password().isEmpty() && datos.password() != null){
            var pass = encoder.encode(datos.password());
            usuario.setPassword(pass);
        }
        if (!promotorId.equals("") && promotorId != null){
            var promotorOptioal = repository.findById(promotorId);
            if (promotorOptioal.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            var promotor = promotorOptioal.get();
            usuario.setPromotor(promotor);
        }
        usuario.actualiza(datos);
        var datosRespuesta = new DatosDeRespuestaUsuario(usuario);
        return ResponseEntity.ok(datosRespuesta);
    }

    @GetMapping("/todos-promotores")
    public ResponseEntity<Page<DatosDeRespuestaUsuario>> listaTodosLosAsesores(@RequestHeader("Authorization") String token, Pageable pageable){
        if (!validacion.validaUsuarioAdmin(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var usuarios = repository.findByRollAndActivoTrue(ROLL.PROMOTOR, pageable).map(DatosDeRespuestaUsuario::new);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/todos-tiendas")
    public ResponseEntity<Page<DatosDeRespuestaUsuario>> listaTodosLasTiendas(@RequestHeader("Authorization") String token, Pageable pageable){
        if (!validacion.validaUsuarioAdmin(token) && !validacion.validaUsuarioPromotor(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var usuarios = repository.findByRollAndActivoTrue(ROLL.TIENDA, pageable).map(DatosDeRespuestaUsuario::new);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/borrar-promotor/{id}")
    @Transactional
    public ResponseEntity borraUsuarios(@PathVariable Long id, @RequestHeader("Authorization") String token){
        if (!validacion.validaUsuarioAdmin(token)){
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var usuarioOptional = repository.findById(id);
        if (usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var usuario = usuarioOptional.get();
        usuario.setActivo();
        return  ResponseEntity.ok().build();
    }
}
