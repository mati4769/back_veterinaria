package com.proyecto.veterinaria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pri_nombre")
    private String priNombre;

    @Column(name = "seg_nombre")
    private String segNombre;

    @Column(name = "apellido_pat")
    private String apellidoPat;

    @Column(name = "apellido_mat")
    private String apellidoMat;

    private String correo;

    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "servicio_interes")
    private String servicioInteres;

    private String modalidad;

    private String contrasena;

}
