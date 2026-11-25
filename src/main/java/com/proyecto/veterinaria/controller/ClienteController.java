package com.proyecto.veterinaria.controller;

import com.proyecto.veterinaria.model.Cliente;
import com.proyecto.veterinaria.service.ClienteService;
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
@RequestMapping("api/v1/clientes") // ENDPOINT
@Tag(name = "Clientes", description = "Gestión de los clientes de la veterinaria")
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping
    @Operation(summary = "Obtener todos los clientes")
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> listar(){
        List<Cliente> clientes = service.getClientes();

        if(clientes.isEmpty())
            return ResponseEntity.noContent().build(); // 204

        List<EntityModel<Cliente>> modelos = new ArrayList<>();
        for(Cliente cliente : clientes) {
            modelos.add(crearModelo(cliente));
        }

        CollectionModel<EntityModel<Cliente>> coleccion = CollectionModel.of(modelos,
                linkTo(methodOn(ClienteController.class).listar()).withSelfRel());

        return ResponseEntity.ok(coleccion); // 200
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por ID")
    public ResponseEntity<EntityModel<Cliente>> buscarPorId(@PathVariable Integer id){
        Optional<Cliente> cliente = service.getCliente(id);

        if(cliente.isPresent())
            return ResponseEntity.ok(crearModelo(cliente.get())); // 200
        else
            return ResponseEntity.notFound().build(); // 404
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente")
    public ResponseEntity<Cliente> agregarCliente(@RequestBody Cliente cliente){
        Cliente nuevo = service.saveCliente(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED) // 201
                .body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente")
    public ResponseEntity<Cliente> editarCliente(@RequestBody Cliente cliente, @PathVariable Integer id){
        try {
            Cliente actualizado = service.updateCliente(id, cliente);
            return ResponseEntity.ok(actualizado); // 200
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id){
        try {
            service.deleteCliente(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    // HATEOAS: agrega los enlaces "self" (este registro) y "todos-los-clientes" (la lista)
    private EntityModel<Cliente> crearModelo(Cliente cliente){
        EntityModel<Cliente> modelo = EntityModel.of(cliente);
        modelo.add(linkTo(methodOn(ClienteController.class).buscarPorId(cliente.getId())).withSelfRel());
        modelo.add(linkTo(methodOn(ClienteController.class).listar()).withRel("todos-los-clientes"));
        return modelo;
    }

}
