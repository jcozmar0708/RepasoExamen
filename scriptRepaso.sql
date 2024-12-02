DROP DATABASE IF EXISTS repaso;
CREATE DATABASE repaso;

CREATE TABLE IF NOT EXISTS clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ordenes (
    id_orden INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    fecha_orden TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE
);

INSERT INTO clientes (nombre, apellido, email)
VALUES
    ('Juan', 'Pérez', 'juan.perez@example.com'),
    ('María', 'Gómez', 'maria.gomez@example.com'),
    ('Carlos', 'Sánchez', 'carlos.sanchez@example.com');

INSERT INTO ordenes (id_cliente, total)
VALUES
    (1, 150.75),
    (2, 200.50),
    (3, 120.00);
