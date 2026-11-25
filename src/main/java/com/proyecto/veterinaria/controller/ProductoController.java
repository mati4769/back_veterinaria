package com.proyecto.veterinaria.controller;

import com.proyecto.veterinaria.model.Producto;
import com.proyecto.veterinaria.service.ProductoService;
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
@RequestMapping("api/v1/productos") // ENDPOINT
@Tag(name = "Productos", description = "Catálogo de productos (servicios con precio)")
public class ProductoController {
    @Autowired
    private ProductoService service;

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listar(){
        List<Producto> productos = service.getProductos();

        if(productos.isEmpty())
            return ResponseEntity.noContent().build(); // 204

        List<EntityModel<Producto>> modelos = new ArrayList<>();
        for(Producto producto : productos) {
            modelos.add(crearModelo(producto));
        }

        CollectionModel<EntityModel<Producto>> coleccion = CollectionModel.of(modelos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel());

        return ResponseEntity.ok(coleccion); // 200
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID")
    public ResponseEntity<EntityModel<Producto>> buscarPorId(@PathVariable Integer id){
        Optional<Producto> producto = service.getProducto(id);

        if(producto.isPresent())
            return ResponseEntity.ok(crearModelo(producto.get())); // 200
        else
            return ResponseEntity.notFound().build(); // 404
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<Producto> agregarProducto(@RequestBody Producto producto){
        Producto nuevo = service.saveProducto(producto);

        return ResponseEntity
                .status(HttpStatus.CREATED) // 201
                .body(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto")
    public ResponseEntity<Producto> editarProducto(@RequestBody Producto producto, @PathVariable Integer id){
        try {
            Producto actualizado = service.updateProducto(id, producto);
            return ResponseEntity.ok(actualizado); // 200
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id){
        try {
            service.deleteProducto(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (Exception e){
            return ResponseEntity.notFound().build(); // 404
        }
    }

    // HATEOAS: agrega los enlaces "self" (este registro) y "todos-los-productos" (la lista)
    private EntityModel<Producto> crearModelo(Producto producto){
        EntityModel<Producto> modelo = EntityModel.of(producto);
        modelo.add(linkTo(methodOn(ProductoController.class).buscarPorId(producto.getId())).withSelfRel());
        modelo.add(linkTo(methodOn(ProductoController.class).listar()).withRel("todos-los-productos"));
        return modelo;
    }

}
