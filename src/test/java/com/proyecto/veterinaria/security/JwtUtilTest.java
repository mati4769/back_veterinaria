package com.proyecto.veterinaria.security;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {
    private final JwtUtil jwtUtil = new JwtUtil();

    private final Faker faker = new Faker();

    @Test
    void generarYLeerToken(){
        // Arrange
        String correo = faker.internet().emailAddress();

        // Act
        String token = jwtUtil.generarToken(correo, "ADMIN");

        // Assert
        assertTrue(jwtUtil.validarToken(token));
        assertEquals(correo, jwtUtil.obtenerCorreo(token));
        assertEquals("ADMIN", jwtUtil.obtenerRol(token));
    }

    @Test
    void tokenInventadoNoEsValido(){
        // Arrange
        String token = faker.lorem().word();

        // Act
        boolean resultado = jwtUtil.validarToken(token);

        // Assert
        assertFalse(resultado);
    }

}
