CREATE DATABASE minimarket;

-- ============================================
-- MINI MARKET PRO
-- MODELO RELACIONAL - POSTGRESQL
-- ============================================


-- ============================================
-- ROL CLIENTE
-- ============================================
CREATE SEQUENCE seq_rolcliente_id INCREMENT 1 START 1;

CREATE TABLE rolCliente (
    id LONG,
    tipoCliente VARCHAR(50) NOT NULL,

    CONSTRAINT pk_rol_cliente PRIMARY KEY (id),
    CONSTRAINT uq_rol_cliente UNIQUE (tipo_cliente)
);


-- ============================================
-- CLIENTE
-- ============================================

CREATE TABLE cliente (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(150),
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    correo_electronico VARCHAR(150),
    rolCliente_id BIGINT,
    CONSTRAINT pk_cliente PRIMARY KEY (id),
    CONSTRAINT fk_cliente_rol FOREIGN KEY (rolCliente_id) REFERENCES rolCliente(id)
);


-- ============================================
-- TELEFONO CLIENTE
-- ============================================

CREATE TABLE telefono_cliente (
    id BIGSERIAL PRIMARY KEY,
    telefono_cliente VARCHAR(20) NOT NULL,
    cliente_id BIGINT NOT NULL,

    CONSTRAINT pk_telefono_cliente PRIMARY KEY (id),
    CONSTRAINT fk_telefono_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);


-- ============================================
-- TPV
-- ============================================

CREATE TABLE tpv (
    id BIGSERIAL PRIMARY KEY,
    nombre_terminal VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    ubicacion VARCHAR(150),

    CONSTRAINT pk_tpv_id PRIMARY KEY (id)
);


-- ============================================
-- ROL TRABAJADOR
-- ============================================

CREATE TABLE rol_trabajador (
    id BIGSERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,

    CONSTRAINT pk_rol_trabajador_id PRIMARY KEY (id)
);


-- ============================================
-- EMPLEADO
-- ============================================

CREATE TABLE empleado (
    id BIGSERIAL PRIMARY KEY,
    codigo_empleado VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(150),
    correo_electronico VARCHAR(150) UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    rol_trabajador_id BIGINT NOT NULL,

    CONSTRAINT pk_empleado_id PRIMARY KEY (id),
    CONSTRAINT fk_empleado_rol FOREIGN KEY (rol_trabajador_id) REFERENCES rol_trabajador(id)
);


-- ============================================
-- TURNO CAJA
-- ============================================

CREATE TABLE turno_caja (
    id BIGSERIAL PRIMARY KEY,
    fondo_caja NUMERIC(10,2) NOT NULL DEFAULT 0,
    estado_caja VARCHAR(30) NOT NULL,
    saldo_real NUMERIC(10,2),
    saldo_esperado NUMERIC(10,2),
    empleado_id BIGINT NOT NULL,

    CONSTRAINT pk_turno_caja_id PRIMARY KEY (id),
    CONSTRAINT fk_turno_empleado FOREIGN KEY (empleado_id) REFERENCES empleado(id)
);


-- ============================================
-- CATEGORIA
-- ============================================

CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),

    CONSTRAINT pk_categoria_id PRIMARY KEY (id)
);


-- ============================================
-- PRODUCTO
-- ============================================

CREATE TABLE producto (
    id BIGSERIAL PRIMARY KEY,
    codigo_producto VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    precio_compra NUMERIC(10,2),
    precio_venta NUMERIC(10,2) NOT NULL,
    stock_actual INTEGER NOT NULL DEFAULT 0,
    stock_minimo INTEGER NOT NULL DEFAULT 0,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    descripcion VARCHAR(255),
    categoria_id BIGINT NOT NULL,

    CONSTRAINT pk_producto_id PRIMARY KEY (id),
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id),

    CONSTRAINT chk_producto_precio_compra
        CHECK (precio_compra IS NULL OR precio_compra >= 0),

    CONSTRAINT chk_producto_precio_venta
        CHECK (precio_venta >= 0),

    CONSTRAINT chk_producto_stock_actual
        CHECK (stock_actual >= 0),

    CONSTRAINT chk_producto_stock_minimo
        CHECK (stock_minimo >= 0)
);


-- ============================================
-- VENTA
-- ============================================

CREATE TABLE venta (
    id BIGSERIAL PRIMARY KEY,
    numero_ticket VARCHAR(50) NOT NULL UNIQUE,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total NUMERIC(10,2) NOT NULL DEFAULT 0,
    estado VARCHAR(30) NOT NULL,
    base_imponible NUMERIC(10,2) NOT NULL DEFAULT 0,
    total_igic NUMERIC(10,2) NOT NULL DEFAULT 0,

    cliente_id BIGINT,
    tpv_id BIGINT NOT NULL,
    turno_caja_id BIGINT NOT NULL,

    CONSTRAINT pk_venta_id PRIMARY KEY (id),
    CONSTRAINT fk_venta_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    CONSTRAINT fk_venta_tpv FOREIGN KEY (tpv_id) REFERENCES tpv(id),
    CONSTRAINT fk_venta_turno FOREIGN KEY (turno_caja_id) REFERENCES turno_caja(id),

    CONSTRAINT chk_venta_total CHECK (total >= 0)
);


-- ============================================
-- DETALLE VENTA
-- ============================================

CREATE TABLE detalle_venta (
    id BIGSERIAL PRIMARY KEY,
    cantidad INTEGER NOT NULL,
    precio_unitario NUMERIC(10,2) NOT NULL,
    tipo_igic NUMERIC(5,2) NOT NULL DEFAULT 0,
    descuento NUMERIC(10,2) NOT NULL DEFAULT 0,
    subtotal NUMERIC(10,2) NOT NULL,

    venta_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,

    CONSTRAINT pk_detalle_venta_id PRIMARY KEY (id),
    CONSTRAINT fk_detalle_venta FOREIGN KEY (venta_id) REFERENCES venta(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_venta_producto FOREIGN KEY (producto_id) REFERENCES producto(id),

    CONSTRAINT chk_detalle_venta_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_venta_precio CHECK (precio_unitario >= 0),
    CONSTRAINT chk_detalle_venta_descuento CHECK (descuento >= 0),
    CONSTRAINT chk_detalle_venta_subtotal CHECK (subtotal >= 0)
);


-- ============================================
-- PAGO
-- ============================================

CREATE TABLE pago (
    id BIGSERIAL PRIMARY KEY,
    metodo VARCHAR(50) NOT NULL,
    importe NUMERIC(10,2) NOT NULL,
    fecha_pago TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    venta_id BIGINT NOT NULL,

    CONSTRAINT pk_pago_id PRIMARY KEY (id),
    CONSTRAINT fk_pago_venta FOREIGN KEY (venta_id) REFERENCES venta(id) ON DELETE CASCADE,

    CONSTRAINT chk_pago_importe CHECK (importe > 0)
);


-- ============================================
-- FACTURA
-- ============================================

CREATE TABLE factura (
    id BIGSERIAL PRIMARY KEY,
    numero_factura VARCHAR(50) NOT NULL UNIQUE,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    cliente_id BIGINT NOT NULL,
    venta_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT pk_factura_id PRIMARY KEY (id),
    CONSTRAINT fk_factura_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    CONSTRAINT fk_factura_venta FOREIGN KEY (venta_id) REFERENCES venta(id)
);


-- ============================================
-- PROVEEDOR
-- ============================================

CREATE TABLE proveedor (
    id BIGSERIAL PRIMARY KEY,
    cif VARCHAR(20) NOT NULL UNIQUE,
    contacto VARCHAR(150),
    nombre_proveedor VARCHAR(150) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    email VARCHAR(150),
    telefono VARCHAR(20),

    CONSTRAINT pk_proveedor_id PRIMARY KEY (id)
);


-- ============================================
-- PRODUCTO PROVEEDOR
-- ============================================

CREATE TABLE producto_proveedor (
    id BIGSERIAL PRIMARY KEY,
    precio_compra NUMERIC(10,2) NOT NULL,
    preferente BOOLEAN NOT NULL DEFAULT FALSE,

    producto_id BIGINT NOT NULL,
    proveedor_id BIGINT NOT NULL,

    CONSTRAINT pk_producto_proveedor_id PRIMARY KEY (id),
    CONSTRAINT fk_producto_proveedor_producto FOREIGN KEY (producto_id) REFERENCES producto(id),
    CONSTRAINT fk_producto_proveedor_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedor(id),

    CONSTRAINT uq_producto_proveedor UNIQUE (producto_id, proveedor_id),

    CONSTRAINT chk_producto_proveedor_precio CHECK (precio_compra >= 0)
);


-- ============================================
-- PEDIDO PROVEEDOR
-- ============================================

CREATE TABLE pedido_proveedor (
    id BIGSERIAL PRIMARY KEY,
    numero_pedido VARCHAR(50) NOT NULL UNIQUE,
    fecha_pedido TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(30) NOT NULL,
    total_estimado NUMERIC(10,2) NOT NULL DEFAULT 0,

    empleado_id BIGINT NOT NULL,

    CONSTRAINT pk_pedido_proveedor_id PRIMARY KEY (id),
    CONSTRAINT fk_pedido_empleado FOREIGN KEY (empleado_id) REFERENCES empleado(id),

    CONSTRAINT chk_pedido_total CHECK (total_estimado >= 0)
);


-- ============================================
-- DETALLE PEDIDO PROVEEDOR
-- ============================================

CREATE TABLE detalle_pedido_proveedor (
    id BIGSERIAL PRIMARY KEY,
    cantidad INTEGER NOT NULL,
    precio_unitario NUMERIC(10,2) NOT NULL,
    descuento NUMERIC(10,2) NOT NULL DEFAULT 0,
    subtotal NUMERIC(10,2) NOT NULL,

    pedido_proveedor_id BIGINT NOT NULL,
    producto_proveedor_id BIGINT NOT NULL,

    CONSTRAINT pk_detalle_pedido_proveedor_id PRIMARY KEY (id),
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (pedido_proveedor_id) REFERENCES pedido_proveedor(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto_proveedor FOREIGN KEY (producto_proveedor_id) REFERENCES producto_proveedor(id),

    CONSTRAINT chk_detalle_pedido_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_pedido_precio CHECK (precio_unitario >= 0),
    CONSTRAINT chk_detalle_pedido_descuento CHECK (descuento >= 0),
    CONSTRAINT chk_detalle_pedido_subtotal CHECK (subtotal >= 0)
);


-- ============================================
-- COMPRA
-- ============================================

CREATE TABLE compra (
    id BIGSERIAL PRIMARY KEY,
    numero_compra VARCHAR(50) UNIQUE,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total NUMERIC(10,2) NOT NULL DEFAULT 0,
    estado VARCHAR(30),

    pedido_proveedor_id BIGINT,
    empleado_id BIGINT NOT NULL,

    CONSTRAINT pk_compra_id PRIMARY KEY (id),
    CONSTRAINT fk_compra_pedido FOREIGN KEY (pedido_proveedor_id) REFERENCES pedido_proveedor(id),
    CONSTRAINT fk_compra_empleado FOREIGN KEY (empleado_id) REFERENCES empleado(id),

    CONSTRAINT chk_compra_total CHECK (total >= 0)
);


-- ============================================
-- DETALLE COMPRA
-- ============================================

CREATE TABLE detalle_compra (
    id BIGSERIAL PRIMARY KEY,
    cantidad INTEGER NOT NULL,
    precio_unitario NUMERIC(10,2) NOT NULL,
    tipo_igic NUMERIC(5,2) NOT NULL DEFAULT 0,
    descuento NUMERIC(10,2) NOT NULL DEFAULT 0,
    subtotal NUMERIC(10,2) NOT NULL,

    compra_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,

    CONSTRAINT pk_detalle_compra_id PRIMARY KEY (id),
    CONSTRAINT fk_detalle_compra FOREIGN KEY (compra_id) REFERENCES compra(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_compra_producto FOREIGN KEY (producto_id) REFERENCES producto(id),

    CONSTRAINT chk_detalle_compra_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_detalle_compra_precio CHECK (precio_unitario >= 0),
    CONSTRAINT chk_detalle_compra_descuento CHECK (descuento >= 0),
    CONSTRAINT chk_detalle_compra_subtotal CHECK (subtotal >= 0)
);


-- ============================================
-- MOVIMIENTO STOCK
-- ============================================

CREATE TABLE movimiento_stock (
    id BIGSERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo VARCHAR(30) NOT NULL,
    cantidad INTEGER NOT NULL,
    motivo VARCHAR(255),

    producto_id BIGINT NOT NULL,
    empleado_id BIGINT,

    CONSTRAINT pk_movimiento_stock_id PRIMARY KEY (id),
    CONSTRAINT fk_movimiento_producto FOREIGN KEY (producto_id) REFERENCES producto(id),
    CONSTRAINT fk_movimiento_empleado FOREIGN KEY (empleado_id) REFERENCES empleado(id),

    CONSTRAINT chk_movimiento_cantidad CHECK (cantidad > 0)
);