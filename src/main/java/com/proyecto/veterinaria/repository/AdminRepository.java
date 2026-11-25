package com.proyecto.veterinaria.repository;

import com.proyecto.veterinaria.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository
        extends JpaRepository<Admin, Integer> {

    // Spring arma la consulta solo a partir del nombre del metodo (busca por la columna correo)
    Optional<Admin> findByCorreo(String correo);
}
