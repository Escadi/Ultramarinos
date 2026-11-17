import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

// ── Modelos e Interfaces (coincidentes con los DTO del backend) ──────────

export interface CategoriaApi {
  id?: number;
  nombreCategoria: string;
  descripcion?: string;
}

export interface RolClienteApi {
  id?: number;
  tipoCliente: string;
}

export interface RolTrabajadorApi {
  id?: number;
  nombreRol: string;
}

export interface ProductoApi {
  id?: number;
  codigoProducto: string;
  nombre: string;
  precioCompra?: number;
  precioVenta: number;
  stockActual?: number;
  stockMinimo?: number;
  activo?: boolean;
  descripcion?: string;
  categoria?: CategoriaApi;
}

export interface ProveedorApi {
  id?: number;
  cif: string;
  contacto?: string;
  nombreProveedor: string;
  activo?: boolean;
  email?: string;
  telefono?: string;
}

export interface ClienteApi {
  id?: number;
  nombre: string;
  apellido?: string;
  fechaCreacion?: string;
  correoElectronico?: string;
  rolCliente?: RolClienteApi;
}

export interface TelefonoClienteApi {
  id?: number;
  telefonoCliente: string;
  cliente?: ClienteApi;
}

export interface EmpleadoApi {
  id?: number;
  codigoEmpleado: string;
  nombre: string;
  apellido?: string;
  correoElectronico?: string;
  contrasenia?: string;
  activo?: boolean;
  rolTrabajador?: RolTrabajadorApi;
}

export interface TpvApi {
  id?: number;
  nombreTerminal: string;
  activo?: boolean;
  ubicacion?: string;
}

export interface TurnoCajaApi {
  id?: number;
  fondoCaja: number;
  estadoCaja: string;
  saldoReal?: number;
  saldoEsperado?: number;
  empleado?: EmpleadoApi;
}

export interface VentaApi {
  id?: number;
  numeroTicket: string;
  fecha?: string;
  total: number;
  estado: string;
  baseImponible: number;
  totalIgic: number;
  cliente?: ClienteApi;
  tpv?: TpvApi;
  turnoCaja?: TurnoCajaApi;
}

export interface DetalleVentaApi {
  id?: number;
  cantidad: number;
  precioUnitario: number;
  tipoIgic?: number;
  descuento?: number;
  subtotal: number;
  venta?: VentaApi;
  producto?: ProductoApi;
}

export interface CompraApi {
  id?: number;
  numeroCompra: string;
  fecha?: string;
  total: number;
  estado: string;
  pedidoProveedor?: PedidoProveedorApi;
  empleado?: EmpleadoApi;
}

export interface DetalleCompraApi {
  id?: number;
  cantidad: number;
  precioUnitario: number;
  tipoIgic?: number;
  descuento?: number;
  subtotal: number;
  compra?: CompraApi;
  producto?: ProductoApi;
}

export interface PedidoProveedorApi {
  id?: number;
  numeroPedido: string;
  fechaPedido?: string;
  estado: string;
  totalEstimado: number;
  empleado?: EmpleadoApi;
}

export interface DetallePedidoProveedorApi {
  id?: number;
  cantidad: number;
  precioUnitario: number;
  descuento?: number;
  subtotal: number;
  pedidoProveedor?: PedidoProveedorApi;
  productoProveedor?: ProductoProveedorApi;
}

export interface ProductoProveedorApi {
  id?: number;
  precioCompra: number;
  preferente?: boolean;
  producto?: ProductoApi;
  proveedor?: ProveedorApi;
}

export interface FacturaApi {
  id?: number;
  numeroFactura: string;
  fecha?: string;
  cliente?: ClienteApi;
  venta?: VentaApi;
}

export interface PagoApi {
  id?: number;
  metodo: string;
  importe: number;
  fechaPago?: string;
  venta?: VentaApi;
}

export interface MovimientoStockApi {
  id?: number;
  fecha?: string;
  tipo: string;
  cantidad: number;
  motivo?: string;
  producto?: ProductoApi;
  empleado?: EmpleadoApi;
}

export interface LoginRequestApi {
  codigoEmpleado: string;
  password: string;
}

export interface LoginResponseApi {
  token: string;
  id: number;
  codigoEmpleado: string;
  nombre: string;
  apellido: string;
  correoElectronico: string;
  rol: string;
}

/**
 * Servicio Centralizado para todas las APIs REST del Backend
 */
@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private readonly base = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) {}

  // ═════════════════════════════════════════════════════════════════════════
  // ── AUTENTICACIÓN / LOGIN
  // ═════════════════════════════════════════════════════════════════════════
  login(credentials: LoginRequestApi): Observable<LoginResponseApi> {
    return this.http.post<LoginResponseApi>(`${this.base}/auth/login`, credentials);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── CATEGORÍAS
  // ═════════════════════════════════════════════════════════════════════════
  getCategorias(): Observable<CategoriaApi[]> {
    return this.http.get<CategoriaApi[]>(`${this.base}/categoria`);
  }

  getCategoria(id: number): Observable<CategoriaApi> {
    return this.http.get<CategoriaApi>(`${this.base}/categoria/${id}`);
  }

  postCategoria(categoria: CategoriaApi): Observable<CategoriaApi> {
    return this.http.post<CategoriaApi>(`${this.base}/categoria`, categoria);
  }

  putCategoria(id: number, categoria: CategoriaApi): Observable<CategoriaApi> {
    return this.http.put<CategoriaApi>(`${this.base}/categoria/${id}`, categoria);
  }

  deleteCategoria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/categoria/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── PRODUCTOS
  // ═════════════════════════════════════════════════════════════════════════
  getProductos(): Observable<ProductoApi[]> {
    return this.http.get<ProductoApi[]>(`${this.base}/producto`);
  }

  getProducto(id: number): Observable<ProductoApi> {
    return this.http.get<ProductoApi>(`${this.base}/producto/${id}`);
  }

  getProductoPorCodigo(codigo: string): Observable<ProductoApi> {
    return this.http.get<ProductoApi>(`${this.base}/producto/codigo/${codigo}`);
  }

  getProductosPorCategoria(nombreCategoria: string): Observable<ProductoApi[]> {
    return this.http.get<ProductoApi[]>(`${this.base}/producto/categoria/${nombreCategoria}`);
  }

  postProducto(producto: ProductoApi): Observable<ProductoApi> {
    return this.http.post<ProductoApi>(`${this.base}/producto`, producto);
  }

  putProducto(id: number, producto: ProductoApi): Observable<ProductoApi> {
    return this.http.put<ProductoApi>(`${this.base}/producto/${id}`, producto);
  }

  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/producto/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── PROVEEDORES
  // ═════════════════════════════════════════════════════════════════════════
  getProveedores(): Observable<ProveedorApi[]> {
    return this.http.get<ProveedorApi[]>(`${this.base}/proveedor`);
  }

  getProveedor(id: number): Observable<ProveedorApi> {
    return this.http.get<ProveedorApi>(`${this.base}/proveedor/${id}`);
  }

  postProveedor(proveedor: ProveedorApi): Observable<ProveedorApi> {
    return this.http.post<ProveedorApi>(`${this.base}/proveedor`, proveedor);
  }

  putProveedor(id: number, proveedor: ProveedorApi): Observable<ProveedorApi> {
    return this.http.put<ProveedorApi>(`${this.base}/proveedor/${id}`, proveedor);
  }

  deleteProveedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/proveedor/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── CLIENTES
  // ═════════════════════════════════════════════════════════════════════════
  getClientes(): Observable<ClienteApi[]> {
    return this.http.get<ClienteApi[]>(`${this.base}/cliente`);
  }

  getCliente(id: number): Observable<ClienteApi> {
    return this.http.get<ClienteApi>(`${this.base}/cliente/${id}`);
  }

  postCliente(cliente: ClienteApi): Observable<ClienteApi> {
    return this.http.post<ClienteApi>(`${this.base}/cliente`, cliente);
  }

  putCliente(id: number, cliente: ClienteApi): Observable<ClienteApi> {
    return this.http.put<ClienteApi>(`${this.base}/cliente/${id}`, cliente);
  }

  deleteCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/cliente/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── ROLES DE CLIENTE
  // ═════════════════════════════════════════════════════════════════════════
  getRolesCliente(): Observable<RolClienteApi[]> {
    return this.http.get<RolClienteApi[]>(`${this.base}/rol-cliente`);
  }

  getRolCliente(id: number): Observable<RolClienteApi> {
    return this.http.get<RolClienteApi>(`${this.base}/rol-cliente/${id}`);
  }

  postRolCliente(rol: RolClienteApi): Observable<RolClienteApi> {
    return this.http.post<RolClienteApi>(`${this.base}/rol-cliente`, rol);
  }

  putRolCliente(id: number, rol: RolClienteApi): Observable<RolClienteApi> {
    return this.http.put<RolClienteApi>(`${this.base}/rol-cliente/${id}`, rol);
  }

  deleteRolCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/rol-cliente/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── TELÉFONOS DE CLIENTE
  // ═════════════════════════════════════════════════════════════════════════
  getTelefonosCliente(): Observable<TelefonoClienteApi[]> {
    return this.http.get<TelefonoClienteApi[]>(`${this.base}/telefono-cliente`);
  }

  getTelefonosPorCliente(clienteId: number): Observable<TelefonoClienteApi[]> {
    return this.http.get<TelefonoClienteApi[]>(`${this.base}/telefono-cliente/cliente/${clienteId}`);
  }

  postTelefonoCliente(telefono: TelefonoClienteApi): Observable<TelefonoClienteApi> {
    return this.http.post<TelefonoClienteApi>(`${this.base}/telefono-cliente`, telefono);
  }

  putTelefonoCliente(id: number, telefono: TelefonoClienteApi): Observable<TelefonoClienteApi> {
    return this.http.put<TelefonoClienteApi>(`${this.base}/telefono-cliente/${id}`, telefono);
  }

  deleteTelefonoCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/telefono-cliente/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── EMPLEADOS
  // ═════════════════════════════════════════════════════════════════════════
  getEmpleados(): Observable<EmpleadoApi[]> {
    return this.http.get<EmpleadoApi[]>(`${this.base}/empleado`);
  }

  getEmpleadoPorCodigo(codigo: string): Observable<EmpleadoApi> {
    return this.http.get<EmpleadoApi>(`${this.base}/empleado/codigo/${codigo}`);
  }

  postEmpleado(empleado: EmpleadoApi): Observable<EmpleadoApi> {
    return this.http.post<EmpleadoApi>(`${this.base}/empleado`, empleado);
  }

  putEmpleado(id: number, empleado: EmpleadoApi): Observable<EmpleadoApi> {
    return this.http.put<EmpleadoApi>(`${this.base}/empleado/${id}`, empleado);
  }

  deleteEmpleado(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/empleado/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── ROLES DE TRABAJADOR
  // ═════════════════════════════════════════════════════════════════════════
  getRolesTrabajador(): Observable<RolTrabajadorApi[]> {
    return this.http.get<RolTrabajadorApi[]>(`${this.base}/rol-trabajador`);
  }

  getRolTrabajador(id: number): Observable<RolTrabajadorApi> {
    return this.http.get<RolTrabajadorApi>(`${this.base}/rol-trabajador/${id}`);
  }

  postRolTrabajador(rol: RolTrabajadorApi): Observable<RolTrabajadorApi> {
    return this.http.post<RolTrabajadorApi>(`${this.base}/rol-trabajador`, rol);
  }

  putRolTrabajador(id: number, rol: RolTrabajadorApi): Observable<RolTrabajadorApi> {
    return this.http.put<RolTrabajadorApi>(`${this.base}/rol-trabajador/${id}`, rol);
  }

  deleteRolTrabajador(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/rol-trabajador/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── TPV
  // ═════════════════════════════════════════════════════════════════════════
  getTpvs(): Observable<TpvApi[]> {
    return this.http.get<TpvApi[]>(`${this.base}/tpv`);
  }

  getTpv(id: number): Observable<TpvApi> {
    return this.http.get<TpvApi>(`${this.base}/tpv/${id}`);
  }

  postTpv(tpv: TpvApi): Observable<TpvApi> {
    return this.http.post<TpvApi>(`${this.base}/tpv`, tpv);
  }

  putTpv(id: number, tpv: TpvApi): Observable<TpvApi> {
    return this.http.put<TpvApi>(`${this.base}/tpv/${id}`, tpv);
  }

  deleteTpv(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/tpv/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── TURNO DE CAJA
  // ═════════════════════════════════════════════════════════════════════════
  getTurnosCaja(): Observable<TurnoCajaApi[]> {
    return this.http.get<TurnoCajaApi[]>(`${this.base}/turno-caja`);
  }

  getTurnoCaja(id: number): Observable<TurnoCajaApi> {
    return this.http.get<TurnoCajaApi>(`${this.base}/turno-caja/${id}`);
  }

  postTurnoCaja(turno: TurnoCajaApi): Observable<TurnoCajaApi> {
    return this.http.post<TurnoCajaApi>(`${this.base}/turno-caja`, turno);
  }

  putTurnoCaja(id: number, turno: TurnoCajaApi): Observable<TurnoCajaApi> {
    return this.http.put<TurnoCajaApi>(`${this.base}/turno-caja/${id}`, turno);
  }

  deleteTurnoCaja(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/turno-caja/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── VENTAS
  // ═════════════════════════════════════════════════════════════════════════
  getVentas(): Observable<VentaApi[]> {
    return this.http.get<VentaApi[]>(`${this.base}/venta`);
  }

  getVenta(id: number): Observable<VentaApi> {
    return this.http.get<VentaApi>(`${this.base}/venta/${id}`);
  }

  postVenta(venta: VentaApi): Observable<VentaApi> {
    return this.http.post<VentaApi>(`${this.base}/venta`, venta);
  }

  putVenta(id: number, venta: VentaApi): Observable<VentaApi> {
    return this.http.put<VentaApi>(`${this.base}/venta/${id}`, venta);
  }

  deleteVenta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/venta/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── DETALLES DE VENTA
  // ═════════════════════════════════════════════════════════════════════════
  getDetallesVenta(): Observable<DetalleVentaApi[]> {
    return this.http.get<DetalleVentaApi[]>(`${this.base}/detalle-venta`);
  }

  getDetallesPorVenta(ventaId: number): Observable<DetalleVentaApi[]> {
    return this.http.get<DetalleVentaApi[]>(`${this.base}/detalle-venta/venta/${ventaId}`);
  }

  postDetalleVenta(detalle: DetalleVentaApi): Observable<DetalleVentaApi> {
    return this.http.post<DetalleVentaApi>(`${this.base}/detalle-venta`, detalle);
  }

  putDetalleVenta(id: number, detalle: DetalleVentaApi): Observable<DetalleVentaApi> {
    return this.http.put<DetalleVentaApi>(`${this.base}/detalle-venta/${id}`, detalle);
  }

  deleteDetalleVenta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/detalle-venta/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── COMPRAS
  // ═════════════════════════════════════════════════════════════════════════
  getCompras(): Observable<CompraApi[]> {
    return this.http.get<CompraApi[]>(`${this.base}/compra`);
  }

  getCompra(id: number): Observable<CompraApi> {
    return this.http.get<CompraApi>(`${this.base}/compra/${id}`);
  }

  postCompra(compra: CompraApi): Observable<CompraApi> {
    return this.http.post<CompraApi>(`${this.base}/compra`, compra);
  }

  putCompra(id: number, compra: CompraApi): Observable<CompraApi> {
    return this.http.put<CompraApi>(`${this.base}/compra/${id}`, compra);
  }

  deleteCompra(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/compra/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── DETALLES DE COMPRA
  // ═════════════════════════════════════════════════════════════════════════
  getDetallesCompra(): Observable<DetalleCompraApi[]> {
    return this.http.get<DetalleCompraApi[]>(`${this.base}/detalle-compra`);
  }

  getDetallesPorCompra(compraId: number): Observable<DetalleCompraApi[]> {
    return this.http.get<DetalleCompraApi[]>(`${this.base}/detalle-compra/compra/${compraId}`);
  }

  postDetalleCompra(detalle: DetalleCompraApi): Observable<DetalleCompraApi> {
    return this.http.post<DetalleCompraApi>(`${this.base}/detalle-compra`, detalle);
  }

  putDetalleCompra(id: number, detalle: DetalleCompraApi): Observable<DetalleCompraApi> {
    return this.http.put<DetalleCompraApi>(`${this.base}/detalle-compra/${id}`, detalle);
  }

  deleteDetalleCompra(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/detalle-compra/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── PEDIDOS A PROVEEDOR
  // ═════════════════════════════════════════════════════════════════════════
  getPedidosProveedor(): Observable<PedidoProveedorApi[]> {
    return this.http.get<PedidoProveedorApi[]>(`${this.base}/pedido-proveedor`);
  }

  getPedidoProveedor(id: number): Observable<PedidoProveedorApi> {
    return this.http.get<PedidoProveedorApi>(`${this.base}/pedido-proveedor/${id}`);
  }

  postPedidoProveedor(pedido: PedidoProveedorApi): Observable<PedidoProveedorApi> {
    return this.http.post<PedidoProveedorApi>(`${this.base}/pedido-proveedor`, pedido);
  }

  putPedidoProveedor(id: number, pedido: PedidoProveedorApi): Observable<PedidoProveedorApi> {
    return this.http.put<PedidoProveedorApi>(`${this.base}/pedido-proveedor/${id}`, pedido);
  }

  deletePedidoProveedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/pedido-proveedor/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── DETALLES DE PEDIDO A PROVEEDOR
  // ═════════════════════════════════════════════════════════════════════════
  getDetallesPedidoProveedor(): Observable<DetallePedidoProveedorApi[]> {
    return this.http.get<DetallePedidoProveedorApi[]>(`${this.base}/detalle-pedido-proveedor`);
  }

  getDetallesPorPedidoProveedor(pedidoId: number): Observable<DetallePedidoProveedorApi[]> {
    return this.http.get<DetallePedidoProveedorApi[]>(`${this.base}/detalle-pedido-proveedor/pedido/${pedidoId}`);
  }

  postDetallePedidoProveedor(detalle: DetallePedidoProveedorApi): Observable<DetallePedidoProveedorApi> {
    return this.http.post<DetallePedidoProveedorApi>(`${this.base}/detalle-pedido-proveedor`, detalle);
  }

  putDetallePedidoProveedor(id: number, detalle: DetallePedidoProveedorApi): Observable<DetallePedidoProveedorApi> {
    return this.http.put<DetallePedidoProveedorApi>(`${this.base}/detalle-pedido-proveedor/${id}`, detalle);
  }

  deleteDetallePedidoProveedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/detalle-pedido-proveedor/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── PRODUCTOS POR PROVEEDOR
  // ═════════════════════════════════════════════════════════════════════════
  getProductosProveedor(): Observable<ProductoProveedorApi[]> {
    return this.http.get<ProductoProveedorApi[]>(`${this.base}/producto-proveedor`);
  }

  getProductosPorProducto(productoId: number): Observable<ProductoProveedorApi[]> {
    return this.http.get<ProductoProveedorApi[]>(`${this.base}/producto-proveedor/producto/${productoId}`);
  }

  getProductosPorProveedor(proveedorId: number): Observable<ProductoProveedorApi[]> {
    return this.http.get<ProductoProveedorApi[]>(`${this.base}/producto-proveedor/proveedor/${proveedorId}`);
  }

  postProductoProveedor(relacion: ProductoProveedorApi): Observable<ProductoProveedorApi> {
    return this.http.post<ProductoProveedorApi>(`${this.base}/producto-proveedor`, relacion);
  }

  putProductoProveedor(id: number, relacion: ProductoProveedorApi): Observable<ProductoProveedorApi> {
    return this.http.put<ProductoProveedorApi>(`${this.base}/producto-proveedor/${id}`, relacion);
  }

  deleteProductoProveedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/producto-proveedor/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── FACTURAS
  // ═════════════════════════════════════════════════════════════════════════
  getFacturas(): Observable<FacturaApi[]> {
    return this.http.get<FacturaApi[]>(`${this.base}/factura`);
  }

  getFactura(id: number): Observable<FacturaApi> {
    return this.http.get<FacturaApi>(`${this.base}/factura/${id}`);
  }

  postFactura(factura: FacturaApi): Observable<FacturaApi> {
    return this.http.post<FacturaApi>(`${this.base}/factura`, factura);
  }

  deleteFactura(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/factura/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── PAGOS
  // ═════════════════════════════════════════════════════════════════════════
  getPagos(): Observable<PagoApi[]> {
    return this.http.get<PagoApi[]>(`${this.base}/pago`);
  }

  getPagosPorVenta(ventaId: number): Observable<PagoApi[]> {
    return this.http.get<PagoApi[]>(`${this.base}/pago/venta/${ventaId}`);
  }

  postPago(pago: PagoApi): Observable<PagoApi> {
    return this.http.post<PagoApi>(`${this.base}/pago`, pago);
  }

  putPago(id: number, pago: PagoApi): Observable<PagoApi> {
    return this.http.put<PagoApi>(`${this.base}/pago/${id}`, pago);
  }

  deletePago(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/pago/${id}`);
  }

  // ═════════════════════════════════════════════════════════════════════════
  // ── MOVIMIENTOS DE STOCK
  // ═════════════════════════════════════════════════════════════════════════
  getMovimientosStock(): Observable<MovimientoStockApi[]> {
    return this.http.get<MovimientoStockApi[]>(`${this.base}/movimiento-stock`);
  }

  getMovimientosPorProducto(productoId: number): Observable<MovimientoStockApi[]> {
    return this.http.get<MovimientoStockApi[]>(`${this.base}/movimiento-stock/producto/${productoId}`);
  }

  postMovimientoStock(movimiento: MovimientoStockApi): Observable<MovimientoStockApi> {
    return this.http.post<MovimientoStockApi>(`${this.base}/movimiento-stock`, movimiento);
  }

  deleteMovimientoStock(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/movimiento-stock/${id}`);
  }
}
