BEGIN;
-- 1. Roles de cliente
INSERT INTO rolCliente (id,tipoCliente) VALUES
(1,'Particular'),
(2,'Empresa'),
(3,'Habitual');

-- 2. Clientes
INSERT INTO cliente (id,nombre,apellido,correo_electronico,rolCliente_id) VALUES
(1,'Lucia','Suarez Medina','lucia.suarez@example.com',1),
(2, 'Carlos', 'Rodriguez Perez', 'carlos.rodriguez@example.com', 1),
(3, 'Maria', 'Gonzalez Lopez', 'maria.gonzalez@example.com', 3),
(4, 'Pedro', 'Martinez Diaz', 'pedro.martinez@example.com', 1),
(5, 'Ana', 'Fernandez Garcia', 'ana.fernandez@example.com', 3),
(6, 'Javier', 'Sanchez Torres', 'javier.sanchez@example.com', 1),
(7, 'Laura', 'Ramirez Castro', 'laura.ramirez@example.com', 3),
(8, 'David', 'Morales Herrera', 'david.morales@example.com', 1),
(9, 'Elena', 'Navarro Ruiz', 'elena.navarro@example.com', 1),
(10, 'Miguel', 'Ortega Ramos', 'miguel.ortega@example.com', 3),
(11, 'Sara', 'Vega Santana', 'sara.vega@example.com', 1),
(12, 'Daniel', 'Suarez Cabrera', 'daniel.suarez@example.com', 3),
(13, 'Patricia', 'Medina Leon', 'patricia.medina@example.com', 1),
(14, 'Alejandro', 'Dominguez Gil', 'alejandro.dominguez@example.com', 1),
(15, 'Cristina', 'Reyes Molina', 'cristina.reyes@example.com', 3),
(16, 'Fernando', 'Acosta Martin', 'fernando.acosta@example.com', 1),
(17, 'Beatriz', 'Pena Hernandez', 'beatriz.pena@example.com', 3),
(18, 'Roberto', 'Cruz Delgado', 'roberto.cruz@example.com', 1),
(19, 'Natalia', 'Sosa Cabrera', 'natalia.sosa@example.com', 3),
(20, 'Adrian', 'Mendoza Flores', 'adrian.mendoza@example.com', 1),
(21, 'Monica', 'Castellano Perez', 'monica.castellano@example.com', 2);


-- 3. Telefonos de clientes
INSERT INTO telefono_cliente (id,telefono_cliente,cliente_id) VALUES
(1,'600000101',1),
(2, '600000102', 2),
(3, '600000103', 3),
(4, '600000104', 4),
(5, '600000105', 5),
(6, '600000106', 6),
(7, '600000107', 7),
(8, '600000108', 8),
(9, '600000109', 9),
(10, '600000110', 10),
(11, '600000111', 11),
(12, '600000112', 12),
(13, '600000113', 13),
(14, '600000114', 14),
(15, '600000115', 15),
(16, '600000116', 16),
(17, '600000117', 17),
(18, '600000118', 18),
(19, '600000119', 19),
(20, '600000120', 20),
(21, '600000121', 21);

-- 4. Terminales TPV
INSERT INTO tpv (id,nombre_terminal,activo,ubicacion) VALUES
(1,'TPV Caja 1',TRUE,'Mostrador principal'),
(2,'TPV Caja 2',TRUE,'Mostrador secundario'),
(3,'TPV Almacen',FALSE,'Almacen');

-- 5. Roles de trabajadores
INSERT INTO rol_trabajador (id,nombre_rol) VALUES
(1,'Administrador'),(2,'Cajero'),(3,'Encargado'),(4,'Almacenero');

-- 6. Empleados (hash de contrasenia pendiente de configurar)
INSERT INTO empleado (id,codigo_empleado,nombre,apellido,correo_electronico,contrasenia,activo,rol_trabajador_id) VALUES
(1,'EMP001','Ana','Perez Ruiz','ana.perez@example.com','DEMO_HASH_PENDIENTE',TRUE,1),
(2,'EMP002','Marcos','Torres Diaz','marcos.torres@example.com','DEMO_HASH_PENDIENTE',TRUE,2),
(3,'EMP003','Sara','Lopez Garcia','sara.lopez@example.com','DEMO_HASH_PENDIENTE',TRUE,3),
(4,'EMP004','Ivan','Martin Ramos','ivan.martin@example.com','DEMO_HASH_PENDIENTE',TRUE,4);

-- 7. Turnos de caja
INSERT INTO turno_caja (id,fondo_caja,estado_caja,saldo_real,saldo_esperado,empleado_id) VALUES
(1,100.00,'CERRADA',105.35,105.35,2),
(2,100.00,'CERRADA',104.28,104.28,2),
(3,150.00,'ABIERTA',NULL,NULL,3),
(4,100.00,'CERRADA',108.35,108.35,2);

-- 8. Categorias
INSERT INTO categoria (id,nombre_categoria,descripcion) VALUES
(1,'Papeleria','Articulos para escritura y oficina'),
(2,'Alimentacion','Alimentos envasados'),
(3,'Limpieza','Productos de higiene y limpieza'),
(4,'Bebidas','Bebidas y refrescos'),
(5,'Informatica','Accesorios informaticos');

-- 9. Productos
INSERT INTO producto (id,codigo_producto,nombre,precio_compra,precio_venta,stock_actual,stock_minimo,activo,descripcion,categoria_id) VALUES
(1,'PRD001','Cuaderno A4',1.20,2.50,85,10,TRUE,'Cuaderno de 80 hojas',1),
(2,'PRD002','Boligrafo azul',0.45,1.80,190,20,TRUE,'Boligrafo tinta azul',1),
(3,'PRD003','Detergente 1L',2.10,4.00,42,8,TRUE,'Detergente liquido',3),
(4,'PRD004','Agua mineral 1.5L',0.55,1.50,120,24,TRUE,'Botella de agua mineral',4),
(5,'PRD005','Memoria USB 32GB',2.80,3.00,34,5,TRUE,'Memoria USB de 32 GB',5),
(6,'PRD006','Paquete galletas',0.80,1.95,60,12,TRUE,'Galletas clasicas',2),
(7,'PRD007','Carpeta archivadora',1.05,2.75,55,10,TRUE,'Carpeta de anillas',1),
(8,'PRD008','Teclado USB',6.50,12.90,12,3,TRUE,'Teclado de ordenador',5);

-- 10. Ventas (base imponible + IGIC = total)
INSERT INTO venta (id,numero_ticket,fecha,total,estado,base_imponible,total_igic,cliente_id,tpv_id,turno_caja_id) VALUES
(1,'T-2026-0001','2026-10-12 10:15:00',5.35,'COMPLETADA',5.00,0.35,1,1,1),
(2,'T-2026-0002','2026-10-12 12:40:00',4.28,'COMPLETADA',4.00,0.28,2,1,2),
(3,'T-2026-0003','2026-10-13 09:25:00',4.82,'COMPLETADA',4.50,0.32,3,2,3),
(4,'T-2026-0004','2026-10-13 17:05:00',8.35,'COMPLETADA',7.80,0.55,4,1,4);

-- 11. Detalles de venta (subtotal = precio * cantidad - descuento, antes de IGIC)
INSERT INTO detalle_venta (id,cantidad,precio_unitario,tipo_igic,descuento,subtotal,venta_id,producto_id) VALUES
(1,2,2.50,7.00,0.00,5.00,1,1),
(2,1,4.00,7.00,0.00,4.00,2,3),
(3,3,1.50,7.00,0.00,4.50,3,4),
(4,1,1.80,7.00,0.00,1.80,4,2),
(5,2,3.00,7.00,0.00,6.00,4,5);

-- 12. Pagos (igualan el total de cada venta)
INSERT INTO pago (id,metodo,importe,fecha_pago,venta_id) VALUES
(1,'EFECTIVO',5.35,'2026-10-12 10:16:00',1),
(2,'TARJETA',4.28,'2026-10-12 12:41:00',2),
(3,'TARJETA',4.82,'2026-10-13 09:26:00',3),
(4,'EFECTIVO',8.35,'2026-10-13 17:06:00',4);

-- 13. Facturas
INSERT INTO factura (id,numero_factura,fecha,cliente_id,venta_id) VALUES
(1,'F-2026-0001','2026-10-12 10:20:00',1,1),
(2,'F-2026-0002','2026-10-12 12:45:00',2,2),
(3,'F-2026-0003','2026-10-13 17:15:00',4,4);

COMMIT;