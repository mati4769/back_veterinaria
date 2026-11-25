package com.proyecto.veterinaria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "admin")
public class Admin {
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

    private String contrasena;

}
