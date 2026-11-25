package com.proyecto.veterinaria.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    // clave secreta con la que se firman los tokens (minimo 32 caracteres)
    private final SecretKey clave = Keys.hmacShaKeyFor("veterinaria-san-marcos-clave-secreta-2026".getBytes());

    // el token dura 1 hora (en milisegundos)
    private final long duracion = 1000 * 60 * 60;

    public String generarToken(String correo){
        return Jwts.builder()
                .subject(correo)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + duracion))
                .signWith(clave)
                .compact();
    }

    public String obtenerCorreo(String token){
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validarToken(String token){
        try {
            obtenerCorreo(token); // si el token fue modificado o ya vencio, lanza una excepcion
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
