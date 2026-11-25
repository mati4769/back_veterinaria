package com.proyecto.veterinaria.model;

import lombok.Data;

// Datos que se envian para iniciar sesion (no es una tabla de la base de datos)
@Data
public class Login {
    private String correo;

    private String contrasena;

}
