package com.proyecto.veterinaria.service;

import com.proyecto.veterinaria.model.Producto;
import com.proyecto.veterinaria.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository repository;

    public List<Producto> getProductos(){
        return repository.findAll();
    }

    public Optional<Producto> getProducto(Integer id){
        return repository.findById(id);
    }

    public Producto saveProducto(Producto producto){
        return repository.save(producto);
    }

    public Producto updateProducto(Integer id, Producto producto){
        if(repository.existsById(id)) {
            producto.setId(id);
            return saveProducto(producto);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

    public void deleteProducto(Integer id){
        if(repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

}
