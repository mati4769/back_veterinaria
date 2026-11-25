package com.proyecto.veterinaria.controller;

import com.proyecto.veterinaria.model.Servicio;
import com.proyecto.veterinaria.service.ServicioService;
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
@RequestMapping("api/v1/servicios") // ENDPOINT
@Tag(name = "Servicios", description = "Servicios contratados por los clientes")
public class ServicioController {
    @Autowired
    private ServicioService service;

    @GetMapping
    @Operation(summary = "Obtener todos los servicios")
    public ResponseEntity<CollectionModel<EntityModel<Servicio>>> listar(){
        List<Servicio> servicios = service.getServicios();

        if(servicios.isEmpty())
            return ResponseEntity.noContent().build(); // 204

        List<EntityModel<Servicio>> modelos = new ArrayList<>();
        for(Servicio servicio : servicios) {
            modelos.add(crearModelo(servicio));
        }

        CollectionModel<EntityModel<Servicio>> coleccion = CollectionModel.of(modelos,
                linkTo(methodOn(ServicioController.class).listar()).withSelfRel());

        return ResponseEntity.ok(coleccion); // 200
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un servicio por ID")
    public ResponseEntity<EntityModel<Servicio>> buscarPorId(@PathVariable Integer id){
        Optional<Servicio> servicio = service.getServicio(id);

        if(servicio.isPresent())
            return ResponseEntity.ok(crearModelo(servicio.get())); // 200
        else
            return ResponseEntity.notFound().build(); // 404
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo servicio")
    public ResponseEntity<Servicio> agregarServicio(@RequestBody Servicio servicio){
        Servicio nuevo = service.saveServicio(servicio);

        return ResponseEntity
                .status(HttpStatus.CREATED) // 201
                .body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un servicio")
    public ResponseEntity<Servicio> editarServicio(@RequestBody Servicio servicio, @PathVariable Integer id){
        try {
            Servicio actualizado = service.updateServicio(id, servicio);
            return ResponseEntity.ok(actualizado); // 200
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un servicio")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer id){
        try {
            service.deleteServicio(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    // HATEOAS: agrega los enlaces "self" (este registro) y "todos-los-servicios" (la lista)
    private EntityModel<Servicio> crearModelo(Servicio servicio){
        EntityModel<Servicio> modelo = EntityModel.of(servicio);
        modelo.add(linkTo(methodOn(ServicioController.class).buscarPorId(servicio.getId())).withSelfRel());
        modelo.add(linkTo(methodOn(ServicioController.class).listar()).withRel("todos-los-servicios"));
        return modelo;
    }

}
