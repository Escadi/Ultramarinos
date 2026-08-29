import { Injectable } from '@angular/core';
import { of, BehaviorSubject } from 'rxjs';

// ─── Interfaces Globales ───────────────────────────────────────────────────────
export type TipoMovimientoCaja = 'Entrada' | 'Salida' | 'Sistema';

export interface MovimientoCaja {
  hora: string;
  tipo: TipoMovimientoCaja;
  concepto: string;
  importe: number;
  usuario?: string;
  facturaId?: string; // Para enlazar con ticket de TPV
}

export interface FacturaTPV {
  id: string;
  fecha: string; // ISO date or time
  empleado: string;
  cliente: string;
  metodoCobro: string;
  lineas: LineaTicketTPV[];
  total: number;
}

export interface CajaEstado {
  empleado: string;
  efectivo: number;
  tarjeta: number;
  fondoInicial: number;
  horaApertura: string;
  movimientos: MovimientoCaja[];
  facturas: FacturaTPV[];
  abierta: boolean;
}

export interface LineaTicketTPV {
  producto: any;
  cantidad: number;
  esPorPeso?: boolean;  // línea vendida por peso (fruta/verdura)
  kg?: number;          // peso en kg
  precioKg?: number;    // precio €/kg
}

export interface SesionTPV {
  idSesion: string;
  nombreEmpleado: string;
  lineasTicket: LineaTicketTPV[];
  clienteActual: string;
}

// ⚠️  MODO MOCK — Todos los endpoints están desactivados.
//     Los métodos devuelven datos falsos para poder trabajar el frontend sin backend.
//     Para reactivar el backend, restaura las llamadas a HttpClient.

@Injectable({
  providedIn: 'root',
})
export class Myservice {

  constructor() { }

  // ─────────────────────────────────────────────────────────────────────────────
  // ESTADO GLOBAL: CAJA Y TPV
  // ─────────────────────────────────────────────────────────────────────────────

  private _cajasActivas = new BehaviorSubject<CajaEstado[]>([]);
  public cajasActivas$ = this._cajasActivas.asObservable();

  private _sesionesTPV = new BehaviorSubject<SesionTPV[]>([]);
  public sesionesTPV$ = this._sesionesTPV.asObservable();

  // ─── Lógica Caja ───
  abrirCaja(empleado: string, fondoInicial: number) {
    const cajas = this._cajasActivas.getValue();
    // Validar si ya tiene caja
    if (cajas.find(c => c.empleado === empleado && c.abierta)) return;

    const ahora = new Date();
    const nuevaCaja: CajaEstado = {
      empleado,
      efectivo: fondoInicial,
      tarjeta: 0,
      fondoInicial,
      horaApertura: ahora.toTimeString().slice(0, 5),
      movimientos: [
        { hora: ahora.toTimeString().slice(0, 5), tipo: 'Sistema', concepto: 'Apertura de Caja (Fondo Inicial)', importe: fondoInicial, usuario: empleado }
      ],
      facturas: [],
      abierta: true
    };
    
    this._cajasActivas.next([...cajas, nuevaCaja]);

    // Automáticamente abrir sesión en TPV
    this._abrirSesionTPV(empleado);
  }

  cerrarCaja(empleado: string, descuadreEfectivo: number, descuadreTarjeta: number) {
    const cajas = this._cajasActivas.getValue();
    const cajaIndex = cajas.findIndex(c => c.empleado === empleado && c.abierta);
    if (cajaIndex === -1) return;

    const caja = cajas[cajaIndex];
    const ahora = new Date().toTimeString().slice(0, 5);

    if (descuadreEfectivo !== 0) {
      caja.movimientos.unshift({ hora: ahora, tipo: 'Sistema', concepto: descuadreEfectivo > 0 ? 'Descuadre (Sobrante Efectivo)' : 'Descuadre (Faltante Efectivo)', importe: descuadreEfectivo, usuario: empleado });
      caja.efectivo += descuadreEfectivo;
    }

    if (descuadreTarjeta !== 0) {
      caja.movimientos.unshift({ hora: ahora, tipo: 'Sistema', concepto: descuadreTarjeta > 0 ? 'Descuadre (Sobrante Tarjeta)' : 'Descuadre (Faltante Tarjeta)', importe: descuadreTarjeta, usuario: empleado });
      caja.tarjeta += descuadreTarjeta;
    }

    caja.movimientos.unshift({ hora: ahora, tipo: 'Sistema', concepto: 'Cierre de Caja', importe: 0, usuario: empleado });
    caja.abierta = false;

    this._cajasActivas.next([...cajas]);

    // Cerrar sesión TPV si existía y está vacía
    this._cerrarSesionTPV(empleado);
  }

  registrarMovimientoCaja(empleado: string, tipo: TipoMovimientoCaja, concepto: string, importe: number, facturaId?: string) {
    const cajas = this._cajasActivas.getValue();
    const caja = cajas.find(c => c.empleado === empleado && c.abierta);
    if (!caja) return;

    caja.movimientos.unshift({
      hora: new Date().toTimeString().slice(0, 5),
      tipo,
      concepto,
      importe: tipo === 'Salida' ? -Math.abs(importe) : Math.abs(importe),
      usuario: empleado,
      facturaId: facturaId
    });

    if (tipo === 'Entrada' || tipo === 'Sistema') {
      caja.efectivo += Math.abs(importe);
    } else {
      caja.efectivo -= Math.abs(importe);
    }
    
    this._cajasActivas.next([...cajas]);
  }

  registrarCobroTPV(empleado: string, total: number, factura?: FacturaTPV) {
    let fId: string | undefined;
    if (factura) {
      const cajas = this._cajasActivas.getValue();
      const caja = cajas.find(c => c.empleado === empleado && c.abierta);
      if (caja) {
        caja.facturas.push(factura);
        fId = factura.id;
        
        // Si el cobro fue con tarjeta, lo sumamos al acumulado de tarjeta
        // En nuestro modelo simplificado, sumamos a caja.tarjeta.
        if (factura.metodoCobro === 'Tarjeta') {
           caja.tarjeta += total;
        }
      }
    }

    // Por simplificación asume cobro en efectivo si no hay método de pago
    // Pero si sabemos el método, ajustamos el tipo de movimiento o simplemente lo registramos como Entrada.
    // Actualmente el sistema solo diferencia caja.efectivo y caja.tarjeta.
    // El método registrarMovimientoCaja afecta a caja.efectivo!
    
    const esTarjeta = factura?.metodoCobro === 'Tarjeta';
    
    if (esTarjeta) {
      // No modifica efectivo, pero registramos el movimiento como sistema/entrada?
      // Lo dejaremos como "Entrada" pero NO debe sumar a efectivo.
      // Para simplificar, si es tarjeta no llamamos a registrarMovimientoCaja,
      // o modificamos registrarMovimientoCaja para que soporte métodos.
      // Modificaremos registrarMovimientoCaja localmente.
      
      const cajas = this._cajasActivas.getValue();
      const caja = cajas.find(c => c.empleado === empleado && c.abierta);
      if (caja) {
        caja.movimientos.unshift({
          hora: new Date().toTimeString().slice(0, 5),
          tipo: 'Entrada',
          concepto: `Venta TPV (Tarjeta)`,
          importe: total,
          usuario: empleado,
          facturaId: fId
        });
        this._cajasActivas.next([...cajas]);
      }
    } else {
      this.registrarMovimientoCaja(empleado, 'Entrada', `Venta TPV (${empleado})`, total, fId);
    }
  }

  // ─── Lógica TPV Interna ───
  private _abrirSesionTPV(empleado: string) {
    const sesiones = this._sesionesTPV.getValue();
    if (sesiones.find(s => s.nombreEmpleado === empleado)) return;
    
    sesiones.push({
      idSesion: Math.random().toString(36).substr(2, 9),
      nombreEmpleado: empleado,
      lineasTicket: [],
      clienteActual: 'Cliente Ocasional'
    });
    this._sesionesTPV.next([...sesiones]);
  }

  private _cerrarSesionTPV(empleado: string) {
    const sesiones = this._sesionesTPV.getValue();
    this._sesionesTPV.next(sesiones.filter(s => s.nombreEmpleado !== empleado || s.lineasTicket.length > 0)); // No cerrar si tiene ticket pendiente
  }

  actualizarSesionTPV(sesiones: SesionTPV[]) {
    this._sesionesTPV.next(sesiones);
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // DATOS MOCK
  // ─────────────────────────────────────────────────────────────────────────────

  private mockProductos = [
    { id: 1, nombre: 'Producto Demo 1', precio: 10.99, stock: 50, categoriaId: 1 },
    { id: 2, nombre: 'Producto Demo 2', precio: 25.50, stock: 20, categoriaId: 2 },
    { id: 3, nombre: 'Producto Demo 3', precio: 5.00,  stock: 100, categoriaId: 1 },
  ];

  private mockCategorias = [
    { id: 1, nombre: 'Categoría A' },
    { id: 2, nombre: 'Categoría B' },
  ];

  private mockCentrosTrabajo = [
    { id: 1, nombre: 'Centro Principal' },
    { id: 2, nombre: 'Centro Secundario' },
  ];

  private mockDepartamentos = [
    { id: 1, nombre: 'Ventas' },
    { id: 2, nombre: 'Almacén' },
    { id: 3, nombre: 'Logística' },
  ];

  private mockProveedores = [
    { id: '1', nombre: 'Proveedor Alpha', contacto: 'alpha@demo.com' },
    { id: '2', nombre: 'Proveedor Beta',  contacto: 'beta@demo.com' },
  ];

  private mockClientes = [
    { id: '1', nombre: 'Cliente Uno',  email: 'uno@demo.com' },
    { id: '2', nombre: 'Cliente Dos',  email: 'dos@demo.com' },
  ];

  private mockDetalleProductos = [
    { id: '1', idDetalleProducto: 1, productoId: 1, descripcion: 'Café tostado de alta calidad',
      producto: { idProducto: 1, nombreProducto: 'Café Molido Premium 250g', descripcion: 'Café tostado', idCategoria: 2 },
      categoria: { idCategoria: 2, nombreCategoria: 'Bebidas' },
      precioCompra: 3.20, precioVenta: 4.95, cantidad: 45, imagen: 'https://images.unsplash.com/photo-1447933601403-0c6688de566e?w=60&q=80' },
    { id: '2', idDetalleProducto: 2, productoId: 2, descripcion: 'Huevos de gallinas camperas',
      producto: { idProducto: 2, nombreProducto: 'Huevos Ecológicos Docena', descripcion: 'Huevos camperos', idCategoria: 3 },
      categoria: { idCategoria: 3, nombreCategoria: 'Frescos' },
      precioCompra: 1.80, precioVenta: 2.80, cantidad: 12, imagen: 'https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?w=60&q=80' },
    { id: '3', idDetalleProducto: 3, productoId: 3, descripcion: 'Pan artesanal de masa madre',
      producto: { idProducto: 3, nombreProducto: 'Pan de Masa Madre', descripcion: 'Pan artesanal', idCategoria: 1 },
      categoria: { idCategoria: 1, nombreCategoria: 'Panadería' },
      precioCompra: 1.10, precioVenta: 2.50, cantidad: 0, imagen: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=60&q=80' },
    { id: '4', idDetalleProducto: 4, productoId: 4, descripcion: 'Queso curado de oveja manchega',
      producto: { idProducto: 4, nombreProducto: 'Queso Manchego Curado 500g', descripcion: 'Queso manchego', idCategoria: 4 },
      categoria: { idCategoria: 4, nombreCategoria: 'Lácteos' },
      precioCompra: 6.50, precioVenta: 9.90, cantidad: 28, imagen: 'https://images.unsplash.com/photo-1552767059-ce182ead6c1b?w=60&q=80' },
    { id: '5', idDetalleProducto: 5, productoId: 5, descripcion: 'Leche entera fresca',
      producto: { idProducto: 5, nombreProducto: 'Leche Entera 1L', descripcion: 'Leche fresca', idCategoria: 4 },
      categoria: { idCategoria: 4, nombreCategoria: 'Lácteos' },
      precioCompra: 0.75, precioVenta: 1.25, cantidad: 60, imagen: 'https://images.unsplash.com/photo-1550583724-b2692b85b150?w=60&q=80' },
    { id: '6', idDetalleProducto: 6, productoId: 6, descripcion: 'Tomates de temporada rama',
      producto: { idProducto: 6, nombreProducto: 'Tomate Rama 1kg', descripcion: 'Tomates frescos', idCategoria: 3 },
      categoria: { idCategoria: 3, nombreCategoria: 'Frescos' },
      precioCompra: 1.20, precioVenta: 2.45, cantidad: 8, imagen: 'https://images.unsplash.com/photo-1546094096-0df4bcaaa337?w=60&q=80' },
    { id: '7', idDetalleProducto: 7, productoId: 7, descripcion: 'Agua mineral sin gas',
      producto: { idProducto: 7, nombreProducto: 'Agua Mineral 1.5L', descripcion: 'Agua mineral', idCategoria: 2 },
      categoria: { idCategoria: 2, nombreCategoria: 'Bebidas' },
      precioCompra: 0.25, precioVenta: 0.65, cantidad: 120, imagen: 'https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=60&q=80' },
    { id: '8', idDetalleProducto: 8, productoId: 8, descripcion: 'Yogur natural sin azúcar',
      producto: { idProducto: 8, nombreProducto: 'Yogur Natural 4x125g', descripcion: 'Yogur natural', idCategoria: 4 },
      categoria: { idCategoria: 4, nombreCategoria: 'Lácteos' },
      precioCompra: 0.55, precioVenta: 0.89, cantidad: 35, imagen: 'https://images.unsplash.com/photo-1488477181946-6428a0291777?w=60&q=80' },
  ];

  private mockCargos = [
    { id: 1, nombre: 'Gerente' },
    { id: 2, nombre: 'Operario' },
    { id: 3, nombre: 'Administrativo' },
  ];

  private mockWorkers = [
    { id: 1, nombre: 'Trabajador Demo 1', cargoId: 1, departamentoId: 1 },
    { id: 2, nombre: 'Trabajador Demo 2', cargoId: 2, departamentoId: 2 },
  ];

  private mockPedidos = [
    { id: '1', idPedido: 'PED-1042', estado: 'Pendiente',   fechaPedido: '2026-08-21',
      cliente:      { cifCliente: 'C001', nombre: 'María García',    email: 'maria@demo.com', telefono: '600111222' },
      empleado:     { nombre: 'Carlos', apellido: 'López',   centroTrabajo: { nombreCentro: 'Centro Norte' } },
      centroTrabajo:{ idCentro: '1', nombreCentro: 'Centro Norte' } },
    { id: '2', idPedido: 'PED-1041', estado: 'En proceso',  fechaPedido: '2026-08-20',
      cliente:      { cifCliente: 'C002', nombre: 'Juan Martínez',   email: 'juan@demo.com',  telefono: '600333444' },
      empleado:     { nombre: 'Ana',    apellido: 'Pérez',   centroTrabajo: { nombreCentro: 'Centro Sur' } },
      centroTrabajo:{ idCentro: '2', nombreCentro: 'Centro Sur' } },
    { id: '3', idPedido: 'PED-1040', estado: 'Enviado',     fechaPedido: '2026-08-19',
      cliente:      { cifCliente: 'C003', nombre: 'Laura Sánchez',   email: 'laura@demo.com', telefono: '600555666' },
      empleado:     { nombre: 'Pedro',  apellido: 'Ruiz',    centroTrabajo: { nombreCentro: 'Centro Este' } },
      centroTrabajo:{ idCentro: '3', nombreCentro: 'Centro Este' } },
    { id: '4', idPedido: 'PED-1039', estado: 'Entregado',   fechaPedido: '2026-08-18',
      cliente:      { cifCliente: 'C004', nombre: 'Roberto Fernández', email: 'roberto@demo.com', telefono: '600777888' },
      empleado:     { nombre: 'Isabel', apellido: 'Gil',     centroTrabajo: { nombreCentro: 'Centro Oeste' } },
      centroTrabajo:{ idCentro: '1', nombreCentro: 'Centro Norte' } },
    { id: '5', idPedido: 'PED-1038', estado: 'Cancelado',   fechaPedido: '2026-08-17',
      cliente:      { cifCliente: 'C005', nombre: 'Carmen Torres',   email: 'carmen@demo.com', telefono: '600999000' },
      empleado:     { nombre: 'Miguel', apellido: 'Díaz',    centroTrabajo: { nombreCentro: 'Centro Sur' } },
      centroTrabajo:{ idCentro: '2', nombreCentro: 'Centro Sur' } },
    { id: '6', idPedido: 'PED-1037', estado: 'Entregado',   fechaPedido: '2026-08-16',
      cliente:      { cifCliente: 'C001', nombre: 'María García',    email: 'maria@demo.com', telefono: '600111222' },
      empleado:     { nombre: 'Carlos', apellido: 'López',   centroTrabajo: { nombreCentro: 'Centro Norte' } },
      centroTrabajo:{ idCentro: '3', nombreCentro: 'Centro Este' } },
  ];

  private mockDetallePedidos = [
    { id: 1, pedidoId: '1', productoId: 1, cantidad: 2 },
    { id: 2, pedidoId: '2', productoId: 3, cantidad: 5 },
  ];

  private mockDetallesCarrito = [
    { id: 1, clienteId: '1', productoId: 2, cantidad: 1 },
  ];

  private mockOrdenesEntrega = [
    { id: 1, pedidoId: '1', conductorId: 1, estado: 'En ruta' },
  ];

  private mockConductores = [
    { id: 1, nombre: 'Conductor Demo 1', licencia: 'B' },
    { id: 2, nombre: 'Conductor Demo 2', licencia: 'C' },
  ];

  private mockTiposVehiculo = [
    { id: 1, tipo: 'Furgoneta' },
    { id: 2, tipo: 'Camión' },
  ];

  private mockVehiculos = [
    { id: '1', matricula: 'AAA-001', tipoVehiculoId: 1 },
    { id: '2', matricula: 'BBB-002', tipoVehiculoId: 2 },
  ];

  private mockVehiculoConductor = [
    { id: 1, vehiculoId: '1', conductorId: 1 },
    { id: 2, vehiculoId: '2', conductorId: 2 },
  ];

  // ─────────────────────────────────────────────────────────────────────────────
  // PRODUCTOS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getProductos() {
    return of(this.mockProductos);
  }

  postProductos(producto: any) {
    const nuevo = { ...producto, id: Date.now() };
    this.mockProductos.push(nuevo);
    return of(nuevo);
  }

  putProductos(id: number, producto: any) {
    const idx = this.mockProductos.findIndex(p => p.id === id);
    if (idx !== -1) this.mockProductos[idx] = { ...this.mockProductos[idx], ...producto };
    return of(this.mockProductos[idx]);
  }

  deleteProductos(id: number) {
    const idx = this.mockProductos.findIndex(p => p.id === id);
    if (idx !== -1) this.mockProductos.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // CATEGORÍAS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getCategorias() {
    return of(this.mockCategorias);
  }

  postCategorias(categoria: any) {
    const nuevo = { ...categoria, id: Date.now() };
    this.mockCategorias.push(nuevo);
    return of(nuevo);
  }

  putCategorias(id: number, categoria: any) {
    const idx = this.mockCategorias.findIndex(c => c.id === id);
    if (idx !== -1) this.mockCategorias[idx] = { ...this.mockCategorias[idx], ...categoria };
    return of(this.mockCategorias[idx]);
  }

  deleteCategorias(id: number) {
    const idx = this.mockCategorias.findIndex(c => c.id === id);
    if (idx !== -1) this.mockCategorias.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // CENTROS DE TRABAJO | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getCentrosTrabajo() {
    return of(this.mockCentrosTrabajo);
  }

  postCentrosTrabajo(centroTrabajo: any) {
    const nuevo = { ...centroTrabajo, id: Date.now() };
    this.mockCentrosTrabajo.push(nuevo);
    return of(nuevo);
  }

  putCentrosTrabajo(id: number, centroTrabajo: any) {
    const idx = this.mockCentrosTrabajo.findIndex(c => c.id === id);
    if (idx !== -1) this.mockCentrosTrabajo[idx] = { ...this.mockCentrosTrabajo[idx], ...centroTrabajo };
    return of(this.mockCentrosTrabajo[idx]);
  }

  deleteCentrosTrabajo(id: number) {
    const idx = this.mockCentrosTrabajo.findIndex(c => c.id === id);
    if (idx !== -1) this.mockCentrosTrabajo.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // DEPARTAMENTOS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getDepartamentos() {
    return of(this.mockDepartamentos);
  }

  postDepartamentos(departamento: any) {
    const nuevo = { ...departamento, id: Date.now() };
    this.mockDepartamentos.push(nuevo);
    return of(nuevo);
  }

  putDepartamentos(id: number, departamento: any) {
    const idx = this.mockDepartamentos.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDepartamentos[idx] = { ...this.mockDepartamentos[idx], ...departamento };
    return of(this.mockDepartamentos[idx]);
  }

  deleteDepartamentos(id: number) {
    const idx = this.mockDepartamentos.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDepartamentos.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // PROVEEDORES | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getProveedores() {
    return of(this.mockProveedores);
  }

  postProveedores(proveedor: any) {
    const nuevo = { ...proveedor, id: String(Date.now()) };
    this.mockProveedores.push(nuevo);
    return of(nuevo);
  }

  putProveedores(id: string, proveedor: any) {
    const idx = this.mockProveedores.findIndex(p => p.id === id);
    if (idx !== -1) this.mockProveedores[idx] = { ...this.mockProveedores[idx], ...proveedor };
    return of(this.mockProveedores[idx]);
  }

  deleteProveedores(id: string) {
    const idx = this.mockProveedores.findIndex(p => p.id === id);
    if (idx !== -1) this.mockProveedores.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // CLIENTES | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getClientes() {
    return of(this.mockClientes);
  }

  postClientes(cliente: any) {
    const nuevo = { ...cliente, id: String(Date.now()) };
    this.mockClientes.push(nuevo);
    return of(nuevo);
  }

  putClientes(id: string, cliente: any) {
    const idx = this.mockClientes.findIndex(c => c.id === id);
    if (idx !== -1) this.mockClientes[idx] = { ...this.mockClientes[idx], ...cliente };
    return of(this.mockClientes[idx]);
  }

  deleteClientes(id: string) {
    const idx = this.mockClientes.findIndex(c => c.id === id);
    if (idx !== -1) this.mockClientes.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // DETALLE PRODUCTOS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getDetalleProductos() {
    return of(this.mockDetalleProductos);
  }

  postDetalleProductos(detalleProducto: any) {
    const nuevo = { ...detalleProducto, id: String(Date.now()) };
    this.mockDetalleProductos.push(nuevo);
    return of(nuevo);
  }

  putDetalleProductos(id: string, detalleProducto: any) {
    const idx = this.mockDetalleProductos.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDetalleProductos[idx] = { ...this.mockDetalleProductos[idx], ...detalleProducto };
    return of(this.mockDetalleProductos[idx]);
  }

  deleteDetalleProductos(id: string) {
    const idx = this.mockDetalleProductos.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDetalleProductos.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // CARGOS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getCargos() {
    return of(this.mockCargos);
  }

  postCargo(cargo: any) {
    const nuevo = { ...cargo, id: Date.now() };
    this.mockCargos.push(nuevo);
    return of(nuevo);
  }

  putCargo(id: number, cargo: any) {
    const idx = this.mockCargos.findIndex(c => c.id === id);
    if (idx !== -1) this.mockCargos[idx] = { ...this.mockCargos[idx], ...cargo };
    return of(this.mockCargos[idx]);
  }

  deleteCargo(id: number) {
    const idx = this.mockCargos.findIndex(c => c.id === id);
    if (idx !== -1) this.mockCargos.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // TRABAJADORES | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getAllWorkers() {
    return of(this.mockWorkers);
  }

  postWorker(worker: any) {
    const nuevo = { ...worker, id: Date.now() };
    this.mockWorkers.push(nuevo);
    return of(nuevo);
  }

  putWorker(id: number, worker: any) {
    const idx = this.mockWorkers.findIndex(w => w.id === id);
    if (idx !== -1) this.mockWorkers[idx] = { ...this.mockWorkers[idx], ...worker };
    return of(this.mockWorkers[idx]);
  }

  deleteWorker(id: number) {
    const idx = this.mockWorkers.findIndex(w => w.id === id);
    if (idx !== -1) this.mockWorkers.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // PEDIDOS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getPedidos() {
    return of(this.mockPedidos);
  }

  postPedidos(pedido: any) {
    const nuevo = { ...pedido, id: String(Date.now()) };
    this.mockPedidos.push(nuevo);
    return of(nuevo);
  }

  putPedidos(id: string, pedido: any) {
    const idx = this.mockPedidos.findIndex(p => p.id === id);
    if (idx !== -1) this.mockPedidos[idx] = { ...this.mockPedidos[idx], ...pedido };
    return of(this.mockPedidos[idx]);
  }

  deletePedidos(id: string) {
    const idx = this.mockPedidos.findIndex(p => p.id === id);
    if (idx !== -1) this.mockPedidos.splice(idx, 1);
    return of({ success: true });
  }

  generateQR(id: string) {
    return of({ qr: `mock-qr-code-for-pedido-${id}` });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // DETALLE PEDIDOS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getDetallePedidos() {
    return of(this.mockDetallePedidos);
  }

  postDetallePedidos(detallePedido: any) {
    const nuevo = { ...detallePedido, id: Date.now() };
    this.mockDetallePedidos.push(nuevo);
    return of(nuevo);
  }

  putDetallePedidos(id: number, detallePedido: any) {
    const idx = this.mockDetallePedidos.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDetallePedidos[idx] = { ...this.mockDetallePedidos[idx], ...detallePedido };
    return of(this.mockDetallePedidos[idx]);
  }

  deleteDetallePedidos(id: number) {
    const idx = this.mockDetallePedidos.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDetallePedidos.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // DETALLE CARRITO | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getDetallesCarrito() {
    return of(this.mockDetallesCarrito);
  }

  postDetallesCarrito(detalleCarrito: any) {
    const nuevo = { ...detalleCarrito, id: Date.now() };
    this.mockDetallesCarrito.push(nuevo);
    return of(nuevo);
  }

  putDetallesCarrito(id: number, detalleCarrito: any) {
    const idx = this.mockDetallesCarrito.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDetallesCarrito[idx] = { ...this.mockDetallesCarrito[idx], ...detalleCarrito };
    return of(this.mockDetallesCarrito[idx]);
  }

  deleteDetallesCarrito(id: number) {
    const idx = this.mockDetallesCarrito.findIndex(d => d.id === id);
    if (idx !== -1) this.mockDetallesCarrito.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // ÓRDENES DE ENTREGA | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getOrdenesEntrega() {
    return of(this.mockOrdenesEntrega);
  }

  postOrdenesEntrega(ordenEntrega: any) {
    const nuevo = { ...ordenEntrega, id: Date.now() };
    this.mockOrdenesEntrega.push(nuevo);
    return of(nuevo);
  }

  putOrdenesEntrega(id: number, ordenEntrega: any) {
    const idx = this.mockOrdenesEntrega.findIndex(o => o.id === id);
    if (idx !== -1) this.mockOrdenesEntrega[idx] = { ...this.mockOrdenesEntrega[idx], ...ordenEntrega };
    return of(this.mockOrdenesEntrega[idx]);
  }

  deleteOrdenesEntrega(id: number) {
    const idx = this.mockOrdenesEntrega.findIndex(o => o.id === id);
    if (idx !== -1) this.mockOrdenesEntrega.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // CONDUCTORES | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getConductor() {
    return of(this.mockConductores);
  }

  postConductor(conductor: any) {
    const nuevo = { ...conductor, id: Date.now() };
    this.mockConductores.push(nuevo);
    return of(nuevo);
  }

  putConductor(id: number, conductor: any) {
    const idx = this.mockConductores.findIndex(c => c.id === id);
    if (idx !== -1) this.mockConductores[idx] = { ...this.mockConductores[idx], ...conductor };
    return of(this.mockConductores[idx]);
  }

  deleteConductor(id: number) {
    const idx = this.mockConductores.findIndex(c => c.id === id);
    if (idx !== -1) this.mockConductores.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // TIPOS DE VEHÍCULO | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getTipoVehiculo() {
    return of(this.mockTiposVehiculo);
  }

  postTipoVehiculo(tipoVehiculo: any) {
    const nuevo = { ...tipoVehiculo, id: Date.now() };
    this.mockTiposVehiculo.push(nuevo);
    return of(nuevo);
  }

  putTipoVehiculo(id: number, tipoVehiculo: any) {
    const idx = this.mockTiposVehiculo.findIndex(t => t.id === id);
    if (idx !== -1) this.mockTiposVehiculo[idx] = { ...this.mockTiposVehiculo[idx], ...tipoVehiculo };
    return of(this.mockTiposVehiculo[idx]);
  }

  deleteTipoVehiculo(id: number) {
    const idx = this.mockTiposVehiculo.findIndex(t => t.id === id);
    if (idx !== -1) this.mockTiposVehiculo.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // VEHÍCULOS | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getVehiculos() {
    return of(this.mockVehiculos);
  }

  postVehiculos(vehiculo: any) {
    const nuevo = { ...vehiculo, id: String(Date.now()) };
    this.mockVehiculos.push(nuevo);
    return of(nuevo);
  }

  putVehiculos(id: string, vehiculo: any) {
    const idx = this.mockVehiculos.findIndex(v => v.id === id);
    if (idx !== -1) this.mockVehiculos[idx] = { ...this.mockVehiculos[idx], ...vehiculo };
    return of(this.mockVehiculos[idx]);
  }

  deleteVehiculos(id: string) {
    const idx = this.mockVehiculos.findIndex(v => v.id === id);
    if (idx !== -1) this.mockVehiculos.splice(idx, 1);
    return of({ success: true });
  }

  // ─────────────────────────────────────────────────────────────────────────────
  // VEHÍCULO-CONDUCTOR | GET, POST, PUT, DELETE
  // ─────────────────────────────────────────────────────────────────────────────

  getVehiculoConductor() {
    return of(this.mockVehiculoConductor);
  }

  postVehiculoConductor(vehiculoConductor: any) {
    const nuevo = { ...vehiculoConductor, id: Date.now() };
    this.mockVehiculoConductor.push(nuevo);
    return of(nuevo);
  }

  putVehiculoConductor(id: number, vehiculoConductor: any) {
    const idx = this.mockVehiculoConductor.findIndex(v => v.id === id);
    if (idx !== -1) this.mockVehiculoConductor[idx] = { ...this.mockVehiculoConductor[idx], ...vehiculoConductor };
    return of(this.mockVehiculoConductor[idx]);
  }

  deleteVehiculoConductor(id: number) {
    const idx = this.mockVehiculoConductor.findIndex(v => v.id === id);
    if (idx !== -1) this.mockVehiculoConductor.splice(idx, 1);
    return of({ success: true });
  }

}
