package com.proyecto.veterinaria.repository;

import com.proyecto.veterinaria.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository
        extends JpaRepository<Cliente, Integer> {
}
