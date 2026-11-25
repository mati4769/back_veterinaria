package com.proyecto.veterinaria.service;

import com.proyecto.veterinaria.model.Admin;
import com.proyecto.veterinaria.repository.AdminRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private AdminRepository repository;

    @InjectMocks
    private AdminService service;

    private final Faker faker = new Faker();

    // admin con datos generados por DataFaker
    private Admin crearAdmin(){
        return new Admin(
                1,
                faker.name().firstName(),
                null,
                faker.name().lastName(),
                faker.name().lastName(),
                faker.internet().emailAddress(),
                faker.internet().password());
    }

    @Test
    void loginConCredencialesCorrectas(){
        // Arrange
        Admin admin = crearAdmin();
        when(repository.findByCorreo(admin.getCorreo())).thenReturn(Optional.of(admin));

        // Act
        boolean resultado = service.login(admin.getCorreo(), admin.getContrasena());

        // Assert
        assertTrue(resultado);
    }

    @Test
    void loginConContrasenaIncorrecta(){
        // Arrange
        Admin admin = crearAdmin();
        when(repository.findByCorreo(admin.getCorreo())).thenReturn(Optional.of(admin));

        // Act
        boolean resultado = service.login(admin.getCorreo(), "otraContrasena");

        // Assert
        assertFalse(resultado);
    }

    @Test
    void loginConCorreoQueNoExiste(){
        // Arrange
        String correo = faker.internet().emailAddress();
        when(repository.findByCorreo(correo)).thenReturn(Optional.empty());

        // Act
        boolean resultado = service.login(correo, faker.internet().password());

        // Assert
        assertFalse(resultado);
    }

}
