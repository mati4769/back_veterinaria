package com.proyecto.veterinaria.controller;

import com.proyecto.veterinaria.model.Admin;
import com.proyecto.veterinaria.security.JwtUtil;
import com.proyecto.veterinaria.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth") // ENDPOINT
@Tag(name = "Autenticación", description = "Inicio de sesión con JWT")
public class AuthController {
    @Autowired
    private AdminService service;

    @Autowired
    private JwtUtil jwtUtil;

    // se envia solo el correo y la contrasena del admin, ej: {"correo": "admin@duocuc.cl", "contrasena": "Admin1234"}
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener el token JWT")
    public ResponseEntity<Map<String, String>> login(@RequestBody Admin datos){
        if(service.login(datos.getCorreo(), datos.getContrasena())) {
            String token = jwtUtil.generarToken(datos.getCorreo());
            return ResponseEntity.ok(Map.of("token", token)); // 200
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401
    }

}
