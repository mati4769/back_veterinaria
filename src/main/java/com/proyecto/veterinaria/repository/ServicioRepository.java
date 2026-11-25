package com.proyecto.veterinaria.repository;

import com.proyecto.veterinaria.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository
        extends JpaRepository<Servicio, Integer> {
}
