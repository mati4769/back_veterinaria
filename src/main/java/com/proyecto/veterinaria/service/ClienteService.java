package com.proyecto.veterinaria.service;

import com.proyecto.veterinaria.model.Cliente;
import com.proyecto.veterinaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public List<Cliente> getClientes(){
        return repository.findAll();
    }

    public Optional<Cliente> getCliente(Integer id){
        return repository.findById(id);
    }

    public Cliente saveCliente(Cliente cliente){
        return repository.save(cliente);
    }

    public Cliente updateCliente(Integer id, Cliente cliente){
        if(repository.existsById(id)) {
            cliente.setId(id);
            return saveCliente(cliente);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

    public void deleteCliente(Integer id){
        if(repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

}
