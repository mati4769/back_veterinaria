package com.proyecto.veterinaria.controller;

import com.proyecto.veterinaria.model.Admin;
import com.proyecto.veterinaria.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/admins") // ENDPOINT
@Tag(name = "Administradores", description = "Gestión de los administradores")
public class AdminController {
    @Autowired
    private AdminService service;

    @GetMapping
    @Operation(summary = "Obtener todos los admins")
    public ResponseEntity<CollectionModel<EntityModel<Admin>>> listar(){
        List<Admin> admins = service.getAdmins();

        if(admins.isEmpty())
            return ResponseEntity.noContent().build(); // 204

        List<EntityModel<Admin>> modelos = new ArrayList<>();
        for(Admin admin : admins) {
            modelos.add(crearModelo(admin));
        }

        CollectionModel<EntityModel<Admin>> coleccion = CollectionModel.of(modelos,
                linkTo(methodOn(AdminController.class).listar()).withSelfRel());

        return ResponseEntity.ok(coleccion); // 200
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un admin por ID")
    public ResponseEntity<EntityModel<Admin>> buscarPorId(@PathVariable Integer id){
        Optional<Admin> admin = service.getAdmin(id);

        if(admin.isPresent())
            return ResponseEntity.ok(crearModelo(admin.get())); // 200
        else
            return ResponseEntity.notFound().build(); // 404
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo admin")
    public ResponseEntity<Admin> agregarAdmin(@RequestBody Admin admin){
        Admin nuevo = service.saveAdmin(admin);

        return ResponseEntity
                .status(HttpStatus.CREATED) // 201
                .body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un admin")
    public ResponseEntity<Admin> editarAdmin(@RequestBody Admin admin, @PathVariable Integer id){
        try {
            Admin actualizado = service.updateAdmin(id, admin);
            return ResponseEntity.ok(actualizado); // 200
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un admin")
    public ResponseEntity<Void> eliminarAdmin(@PathVariable Integer id){
        try {
            service.deleteAdmin(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    // HATEOAS: agrega los enlaces "self" (este registro) y "todos-los-admins" (la lista)
    private EntityModel<Admin> crearModelo(Admin admin){
        EntityModel<Admin> modelo = EntityModel.of(admin);
        modelo.add(linkTo(methodOn(AdminController.class).buscarPorId(admin.getId())).withSelfRel());
        modelo.add(linkTo(methodOn(AdminController.class).listar()).withRel("todos-los-admins"));
        return modelo;
    }

}
