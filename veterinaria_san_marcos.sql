-- Base de datos: Veterinaria San Marcos (MySQL)

DROP DATABASE IF EXISTS veterinaria_san_marcos;
CREATE DATABASE veterinaria_san_marcos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE veterinaria_san_marcos;

-- =========================
-- TABLAS
-- =========================

-- Administradores (Login y Panel admin)
CREATE TABLE admin (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    pri_nombre   VARCHAR(50)  NOT NULL,
    seg_nombre   VARCHAR(50)  NULL,
    apellido_pat VARCHAR(50)  NOT NULL,
    apellido_mat VARCHAR(50)  NOT NULL,
    correo       VARCHAR(100) NOT NULL,
    contrasena   VARCHAR(100) NOT NULL
);

-- Clientes (formulario de Registro)
CREATE TABLE cliente (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    pri_nombre       VARCHAR(50)  NOT NULL,
    seg_nombre       VARCHAR(50)  NULL,
    apellido_pat     VARCHAR(50)  NOT NULL,
    apellido_mat     VARCHAR(50)  NOT NULL,
    correo           VARCHAR(100) NOT NULL,
    telefono         VARCHAR(15)  NOT NULL,
    fecha_nacimiento DATE         NOT NULL,
    servicio_interes VARCHAR(50)  NOT NULL,
    modalidad        VARCHAR(20)  NOT NULL,
    contrasena       VARCHAR(100) NOT NULL
);

-- Mascotas de los clientes (Registro: nombre y especie de su mascota)
CREATE TABLE mascota (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(50) NOT NULL,
    especie    VARCHAR(20) NOT NULL,
    id_cliente INT         NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Productos del catalogo (Panel admin: agregar, modificar y eliminar)
CREATE TABLE producto (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    titulo      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio      INT          NOT NULL,
    imagen      VARCHAR(255) NOT NULL,
    categoria   VARCHAR(50)  NOT NULL,
    id_admin    INT          NOT NULL,
    FOREIGN KEY (id_admin) REFERENCES admin(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Favoritos (boton "Agregar a favoritos")
CREATE TABLE favorito (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente  INT NOT NULL,
    id_producto INT NOT NULL,
    FOREIGN KEY (id_cliente)  REFERENCES cliente(id)  ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Servicios contratados por los clientes (Carrito de compras, boton "Pagar")
CREATE TABLE servicio (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    fecha      DATE NOT NULL,
    id_cliente INT  NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Productos de cada servicio contratado
CREATE TABLE detalle_servicio (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    cantidad    INT NOT NULL,
    id_servicio INT NOT NULL,
    id_producto INT NOT NULL,
    FOREIGN KEY (id_servicio) REFERENCES servicio(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Citas (formulario "Solicitar una cita")
CREATE TABLE cita (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    motivo     VARCHAR(50) NOT NULL,
    fecha      DATE        NOT NULL,
    hora       TIME        NOT NULL,
    id_mascota INT         NOT NULL,
    FOREIGN KEY (id_mascota) REFERENCES mascota(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Ficha clinica (formulario "Registrar atención"; medicamento y vacuna son opcionales)
CREATE TABLE ficha_clinica (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    diagnostico VARCHAR(255) NOT NULL,
    medicamento VARCHAR(100) NULL,
    vacuna      VARCHAR(100) NULL,
    fecha       DATE         NOT NULL,
    id_mascota  INT          NOT NULL,
    FOREIGN KEY (id_mascota) REFERENCES mascota(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- =========================
-- DATOS
-- =========================

-- Correo y contraseña de src/data/usuarios.js
INSERT INTO admin (pri_nombre, seg_nombre, apellido_pat, apellido_mat, correo, contrasena) VALUES
('Carlos', 'Andrés', 'Muñoz', 'Díaz', 'admin@duocuc.cl', 'Admin1234');

-- Dueños de src/data/citas.js
INSERT INTO cliente (pri_nombre, seg_nombre, apellido_pat, apellido_mat, correo, telefono, fecha_nacimiento, servicio_interes, modalidad, contrasena) VALUES
('Marcela', 'Andrea', 'Rojas', 'Fuentes', 'marcela.rojas@gmail.com', '+56911111111', '1990-05-12', 'vacunacion', 'presencial', 'Marcela1234'),
('Pedro', NULL, 'Soto', 'Vargas', 'pedro.soto@outlook.com', '+56922222222', '1985-09-30', 'general', 'online', 'Pedro1234');

-- Mascotas de src/data/citas.js
INSERT INTO mascota (nombre, especie, id_cliente) VALUES
('Toby', 'perro', 1),
('Michi', 'gato', 2);

-- Productos de src/data/productos.js
INSERT INTO producto (titulo, descripcion, precio, imagen, categoria, id_admin) VALUES
('Consulta general', 'Examen y diagnóstico con nuestros médicos veterinarios.', 15000, 'consulta_general.jpg', 'Atención', 1),
('Vacunación', 'Historial de vacunas al día para perros, gatos y más.', 12000, 'vacunacion.jpg', 'Prevención', 1),
('Cirugía menor', 'Procedimientos quirúrgicos simples con anestesia.', 60000, 'cirugia.jpg', 'Atención', 1),
('Desparasitación', 'Tratamientos preventivos internos y externos.', 8000, 'desparasitacion.jpg', 'Prevención', 1),
('Control de peso', 'Seguimiento periódico del peso de tu mascota.', 10000, 'control_de_peso.jpg', 'Prevención', 1);

INSERT INTO favorito (id_cliente, id_producto) VALUES
(1, 2),
(2, 1),
(2, 5);

INSERT INTO servicio (fecha, id_cliente) VALUES
('2026-10-02', 1),
('2026-10-03', 2),
('2026-10-17', 2);

INSERT INTO detalle_servicio (cantidad, id_servicio, id_producto) VALUES
(1, 1, 2),
(1, 1, 4),
(1, 2, 1),
(1, 3, 5);

-- Citas de src/data/citas.js
INSERT INTO cita (motivo, fecha, hora, id_mascota) VALUES
('Vacunación', '2026-10-02', '10:00', 1),
('Consulta general', '2026-10-03', '16:30', 2);

-- Fichas de src/data/fichas.js
INSERT INTO ficha_clinica (diagnostico, medicamento, vacuna, fecha, id_mascota) VALUES
('Control anual', NULL, 'Antirrábica', '2025-11-26', 1),
('Bajo de peso', 'Suplemento vitamínico', NULL, '2026-10-17', 2);
