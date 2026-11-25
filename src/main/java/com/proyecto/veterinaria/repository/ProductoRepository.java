package com.proyecto.veterinaria.repository;

import com.proyecto.veterinaria.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Integer> {
}
