package com.proyecto.veterinaria.service;

import com.proyecto.veterinaria.model.Producto;
import com.proyecto.veterinaria.repository.ProductoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {
    @Mock
    private ProductoRepository repository; // repositorio falso: no usa la base de datos

    @InjectMocks
    private ProductoService service;

    private final Faker faker = new Faker();

    // producto con datos generados por DataFaker
    private Producto crearProducto(){
        return new Producto(
                faker.number().numberBetween(1, 100),
                faker.commerce().productName(),
                faker.lorem().sentence(),
                faker.number().numberBetween(1000, 60000),
                "logotipo.jpg",
                "Atención",
                1);
    }

    @Test
    void obtenerTodosLosProductos(){
        // Arrange
        List<Producto> productos = List.of(crearProducto(), crearProducto(), crearProducto());
        when(repository.findAll()).thenReturn(productos);

        // Act
        List<Producto> resultado = service.getProductos();

        // Assert
        assertEquals(3, resultado.size());
    }

    @Test
    void buscarProductoPorId(){
        // Arrange
        Producto producto = crearProducto();
        when(repository.findById(producto.getId())).thenReturn(Optional.of(producto));

        // Act
        Optional<Producto> resultado = service.getProducto(producto.getId());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(producto.getTitulo(), resultado.get().getTitulo());
    }

    @Test
    void guardarProducto(){
        // Arrange
        Producto producto = crearProducto();
        when(repository.save(producto)).thenReturn(producto);

        // Act
        Producto resultado = service.saveProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals(producto.getPrecio(), resultado.getPrecio());
    }

    @Test
    void actualizarProductoExistente(){
        // Arrange
        Producto producto = crearProducto();
        when(repository.existsById(10)).thenReturn(true);
        when(repository.save(producto)).thenReturn(producto);

        // Act
        Producto resultado = service.updateProducto(10, producto);

        // Assert
        assertEquals(10, resultado.getId()); // toma el id de la URL
    }

    @Test
    void actualizarProductoQueNoExiste(){
        // Arrange
        Producto producto = crearProducto();
        when(repository.existsById(99)).thenReturn(false);

        // Act + Assert
        assertThrows(RuntimeException.class, () -> service.updateProducto(99, producto));
    }

    @Test
    void eliminarProducto(){
        // Arrange
        when(repository.existsById(5)).thenReturn(true);

        // Act
        service.deleteProducto(5);

        // Assert
        verify(repository).deleteById(5);
    }

    @Test
    void eliminarProductoQueNoExiste(){
        // Arrange
        when(repository.existsById(99)).thenReturn(false);

        // Act + Assert
        assertThrows(RuntimeException.class, () -> service.deleteProducto(99));
    }

}
