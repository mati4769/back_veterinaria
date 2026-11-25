package com.proyecto.veterinaria.repository;

import com.proyecto.veterinaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository
        extends JpaRepository<Cliente, Integer> {

    // Spring arma la consulta solo a partir del nombre del metodo (busca por la columna correo)
    Optional<Cliente> findByCorreo(String correo);
}
