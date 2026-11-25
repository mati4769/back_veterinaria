package com.proyecto.veterinaria.service;

import com.proyecto.veterinaria.model.Cliente;
import com.proyecto.veterinaria.repository.ClienteRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    private final Faker faker = new Faker();

    // cliente con datos generados por DataFaker
    private Cliente crearCliente(){
        return new Cliente(
                1,
                faker.name().firstName(),
                null,
                faker.name().lastName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                "+56912345678",
                LocalDate.of(1990, 1, 1),
                "general",
                "presencial",
                faker.internet().password());
    }

    @Test
    void loginClienteConCredencialesCorrectas(){
        // Arrange
        Cliente cliente = crearCliente();
        when(repository.findByCorreo(cliente.getCorreo())).thenReturn(Optional.of(cliente));

        // Act
        boolean resultado = service.login(cliente.getCorreo(), cliente.getContrasena());

        // Assert
        assertTrue(resultado);
    }

    @Test
    void loginClienteConContrasenaIncorrecta(){
        // Arrange
        Cliente cliente = crearCliente();
        when(repository.findByCorreo(cliente.getCorreo())).thenReturn(Optional.of(cliente));

        // Act
        boolean resultado = service.login(cliente.getCorreo(), "otraContrasena");

        // Assert
        assertFalse(resultado);
    }

}
