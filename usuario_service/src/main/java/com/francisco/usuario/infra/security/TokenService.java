package com.francisco.usuario.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.francisco.usuario.domain.usuario.Usuario;
import com.francisco.usuario.infra.config.SecretConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Autowired
    private SecretConfig secretConfig;

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretConfig.getSecret());
            return JWT.create()
                    .withIssuer("Recargas")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaDeExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public static Instant generarFechaDeExpiracion(){
        return LocalDateTime.now().plusHours(72).toInstant(ZoneOffset.of("-06:00"));
    }

    public String getSubject(String token) {
        if (token == null){
            throw new RuntimeException("Token nullo");
        }
        DecodedJWT verifier =null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretConfig.getSecret());
            verifier = JWT.require(algorithm)
                    .withIssuer("Recargas")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null){
            throw new RuntimeException("Subject invalido");
        }
        return verifier.getSubject();
    }

    public Long getClaim(String token) {
        if (token == null) {
            throw new RuntimeException("Token nullo");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretConfig.getSecret());
            verifier = JWT.require(algorithm)
                    .withIssuer("Recargas")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getClaim("id") == null) {
            throw new RuntimeException("Subject invalido");
        }
        return verifier.getClaim("id").asLong();
    }
}
