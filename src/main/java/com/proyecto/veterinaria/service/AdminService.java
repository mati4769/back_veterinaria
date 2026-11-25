package com.proyecto.veterinaria.service;

import com.proyecto.veterinaria.model.Admin;
import com.proyecto.veterinaria.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository repository;

    public List<Admin> getAdmins(){
        return repository.findAll();
    }

    public Optional<Admin> getAdmin(Integer id){
        return repository.findById(id);
    }

    public Admin saveAdmin(Admin admin){
        return repository.save(admin);
    }

    public Admin updateAdmin(Integer id, Admin admin){
        if(repository.existsById(id)) {
            admin.setId(id);
            return saveAdmin(admin);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

    public void deleteAdmin(Integer id){
        if(repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("No encontrado");
        }
    }

    // JWT: revisa que exista un admin con ese correo y que la contrasena sea la misma
    public boolean login(String correo, String contrasena){
        Optional<Admin> admin = repository.findByCorreo(correo);

        return admin.isPresent() && admin.get().getContrasena().equals(contrasena);
    }

}
