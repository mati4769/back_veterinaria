package com.proyecto.veterinaria.controller;

import com.proyecto.veterinaria.model.Login;
import com.proyecto.veterinaria.security.JwtUtil;
import com.proyecto.veterinaria.service.AdminService;
import com.proyecto.veterinaria.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth") // ENDPOINT
@Tag(name = "Autenticación", description = "Inicio de sesión con JWT (roles ADMIN y USER)")
public class AuthController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JwtUtil jwtUtil;

    // ej: {"correo": "admin@duocuc.cl", "contrasena": "Admin1234"}
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener el token JWT")
    public ResponseEntity<Map<String, String>> login(@RequestBody Login datos){
        String rol;

        // si esta en la tabla admin el rol es ADMIN, y si esta en la tabla cliente el rol es USER
        if(adminService.login(datos.getCorreo(), datos.getContrasena()))
            rol = "ADMIN";
        else if(clienteService.login(datos.getCorreo(), datos.getContrasena()))
            rol = "USER";
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 401

        String token = jwtUtil.generarToken(datos.getCorreo(), rol);
        return ResponseEntity.ok(Map.of("token", token, "rol", rol)); // 200
    }

}
