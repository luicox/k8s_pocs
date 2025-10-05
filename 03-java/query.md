-- Creación de la Base de Datos (Opcional, si no existe)
CREATE DATABASE IF NOT EXISTS tienda_electronica;
USE tienda_electronica;

-- Tabla 1: PRODUCTOS
CREATE TABLE Productos (
    producto_id INT PRIMARY KEY AUTO_INCREMENT, -- Clave Primaria
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    fecha_adicion DATE,
    CHECK (precio > 0) -- Restricción de verificación
);

-- Tabla 2: EMPLEADOS
CREATE TABLE Empleados (
    empleado_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    puesto VARCHAR(50) NOT NULL,
    fecha_contratacion DATE,
    salario DECIMAL(10, 2)
);

-- Tabla 3: INVENTARIO (Relación con Productos)
CREATE TABLE Inventario (
    inventario_id INT PRIMARY KEY AUTO_INCREMENT,
    producto_id INT UNIQUE NOT NULL, -- Clave foránea al Producto, única
    cantidad_en_stock INT NOT NULL,
    ultima_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id)
);

-- Tabla 4: VENTAS (Para registrar las transacciones)
CREATE TABLE Ventas (
    venta_id INT PRIMARY KEY AUTO_INCREMENT,
    fecha_venta DATETIME NOT NULL,
    empleado_id INT, -- Clave foránea al Empleado que realizó la venta
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (empleado_id) REFERENCES Empleados(empleado_id)
);

-- Tabla 5: DETALLE_VENTA (Tabla de muchos a muchos entre Ventas y Productos)
CREATE TABLE Detalle_Venta (
    detalle_id INT PRIMARY KEY AUTO_INCREMENT,
    venta_id INT NOT NULL, -- Clave foránea a la Venta
    producto_id INT NOT NULL, -- Clave foránea al Producto
    cantidad INT NOT NULL,
    precio_unidad DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES Ventas(venta_id),
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    UNIQUE (venta_id, producto_id) -- No se repite el mismo producto en la misma venta
);

INSERT INTO Productos (nombre, descripcion, precio, categoria, fecha_adicion) VALUES
('Laptop Pro X15', 'Portátil de alto rendimiento con 16GB RAM', 1250.00, 'Computadoras', '2024-01-10'),
('Smartphone Galaxy S23', 'Teléfono de gama alta con cámara de 108MP', 899.99, 'Smartphones', '2024-01-15'),
('Audífonos Noise Cancelling', 'Auriculares inalámbricos con cancelación de ruido', 199.50, 'Accesorios', '2024-02-01'),
('Monitor Curvo 27"', 'Monitor LED curvo para juegos', 350.00, 'Monitores', '2024-02-10'),
('Teclado Mecánico RGB', 'Teclado para gaming con switches rojos', 85.99, 'Accesorios', '2024-03-05'),
('Impresora Láser Multifuncional', 'Impresora para oficina con escaner', 220.00, 'Periféricos', '2024-03-20');

INSERT INTO Empleados (nombre, apellido, puesto, fecha_contratacion, salario) VALUES
('Ana', 'García', 'Gerente de Tienda', '2020-05-15', 55000.00),
('Luis', 'Pérez', 'Vendedor Senior', '2021-08-20', 35000.00),
('Marta', 'López', 'Asistente de Inventario', '2022-01-10', 30000.00),
('Javier', 'Rodríguez', 'Vendedor Junior', '2023-11-01', 28000.00);

-- Los IDs de producto corresponden a los insertados previamente
INSERT INTO Inventario (producto_id, cantidad_en_stock) VALUES
(1, 15), -- Laptop
(2, 22), -- Smartphone
(3, 40), -- Audífonos
(4, 10), -- Monitor
(5, 55), -- Teclado
(6, 8);  -- Impresora
-- Venta 1: Ana vende una Laptop y un Teclado
INSERT INTO Ventas (fecha_venta, empleado_id, total) VALUES
('2024-05-01 10:30:00', 1, 1335.99); -- 1250.00 + 85.99 = 1335.99

INSERT INTO Detalle_Venta (venta_id, producto_id, cantidad, precio_unidad) VALUES
(1, 1, 1, 1250.00),
(1, 5, 1, 85.99);

-- Venta 2: Luis vende dos Smartphones y unos Audífonos
INSERT INTO Ventas (fecha_venta, empleado_id, total) VALUES
('2024-05-01 14:45:00', 2, 1999.48); -- (899.99 * 2) + 199.50 = 1999.48

INSERT INTO Detalle_Venta (venta_id, producto_id, cantidad, precio_unidad) VALUES
(2, 2, 2, 899.99),
(2, 3, 1, 199.50);

-- Venta 3: Javier vende una Impresora
INSERT INTO Ventas (fecha_venta, empleado_id, total) VALUES
('2024-05-02 09:00:00', 4, 220.00);

INSERT INTO Detalle_Venta (venta_id, producto_id, cantidad, precio_unidad) VALUES
(3, 6, 1, 220.00);


Concepto a Enseñar	Consulta SQL Sugerida
SELECT simple	SELECT * FROM Productos;
WHERE (filtrado)	SELECT nombre, precio FROM Productos WHERE precio > 500.00;
ORDER BY (ordenamiento)	SELECT nombre, precio FROM Productos ORDER BY precio DESC;
JOIN (unión de tablas)	SELECT p.nombre, i.cantidad_en_stock FROM Productos p JOIN Inventario i ON p.producto_id = i.producto_id;
GROUP BY (agregación)	SELECT categoria, AVG(precio) AS precio_promedio FROM Productos GROUP BY categoria;
JOIN y Agregación	SELECT e.nombre, COUNT(v.venta_id) AS total_ventas FROM Empleados e LEFT JOIN Ventas v ON e.empleado_id = v.empleado_id GROUP BY e.nombre;
SUBQUERY / CTE	SELECT nombre FROM Productos WHERE producto_id IN (SELECT producto_id FROM Detalle_Venta WHERE venta_id = 2);
DATE Functions	SELECT * FROM Ventas WHERE DATE(fecha_venta) = '2024-05-01';
UPDATE y DELETE	UPDATE Productos SET precio = 1300.00 WHERE nombre = 'Laptop Pro X15';
DELETE FROM Inventario WHERE producto_id = 6 AND cantidad_en_stock = 0;