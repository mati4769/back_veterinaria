package com.proyecto.veterinaria.service;

import com.proyecto.veterinaria.model.Servicio;
import com.proyecto.veterinaria.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository repository;

    public List<Servicio> getServicios(){
        return repository.findAll();
    }

    public Optional<Servicio> getServicio(Integer id){
        return repository.findById(id);
    }

    public Servicio saveServicio(Servicio servicio){
        return repository.save(servicio);
    }

    public Servicio updateServicio(Integer id, Servicio servicio){
        if(repository.existsById(id)) {
            servicio.setId(id);
            return saveServicio(servicio);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

    public void deleteServicio(Integer id){
        if(repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

}
