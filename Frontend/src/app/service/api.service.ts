import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

// ── Modelos (reflejan los DTO del backend) ──────────────────────────────
export interface CategoriaApi {
  id?: number;
  nombreCategoria: string;
  descripcion?: string;
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
  categoriaId: number;
  categoriaNombre?: string;
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
  rolClienteId?: number;
  rolClienteTipo?: string;
}

/**
 * Servicio HTTP que conecta el frontend con los controllers REST del backend.
 * Cada entidad expone el CRUD completo: get / getById / post / put / delete.
 */
@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private readonly base = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) { }

  // ── CATEGORÍAS ────────────────────────────────────────────────────────
  getCategorias(): Observable<CategoriaApi[]> {
    return this.http.get<CategoriaApi[]>(`${this.base}/categorias`);
  }

  getCategoria(id: number): Observable<CategoriaApi> {
    return this.http.get<CategoriaApi>(`${this.base}/categorias/${id}`);
  }

  postCategoria(categoria: CategoriaApi): Observable<CategoriaApi> {
    return this.http.post<CategoriaApi>(`${this.base}/categorias`, categoria);
  }

  putCategoria(id: number, categoria: CategoriaApi): Observable<CategoriaApi> {
    return this.http.put<CategoriaApi>(`${this.base}/categorias/${id}`, categoria);
  }

  deleteCategoria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/categorias/${id}`);
  }

  // ── PRODUCTOS ─────────────────────────────────────────────────────────
  getProductos(): Observable<ProductoApi[]> {
    return this.http.get<ProductoApi[]>(`${this.base}/productos`);
  }

  getProducto(id: number): Observable<ProductoApi> {
    return this.http.get<ProductoApi>(`${this.base}/productos/${id}`);
  }

  buscarProductos(nombre: string): Observable<ProductoApi[]> {
    return this.http.get<ProductoApi[]>(`${this.base}/productos/buscar`, {
      params: new HttpParams().set('nombre', nombre),
    });
  }

  getProductosPorCategoria(categoriaId: number): Observable<ProductoApi[]> {
    return this.http.get<ProductoApi[]>(`${this.base}/productos/categoria/${categoriaId}`);
  }

  getProductosActivos(): Observable<ProductoApi[]> {
    return this.http.get<ProductoApi[]>(`${this.base}/productos/activos`);
  }

  getProductosStockBajo(): Observable<ProductoApi[]> {
    return this.http.get<ProductoApi[]>(`${this.base}/productos/stock-bajo`);
  }

  postProducto(producto: ProductoApi): Observable<ProductoApi> {
    return this.http.post<ProductoApi>(`${this.base}/productos`, producto);
  }

  putProducto(id: number, producto: Partial<ProductoApi>): Observable<ProductoApi> {
    return this.http.put<ProductoApi>(`${this.base}/productos/${id}`, producto);
  }

  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/productos/${id}`);
  }

  // ── PROVEEDORES ───────────────────────────────────────────────────────
  getProveedores(): Observable<ProveedorApi[]> {
    return this.http.get<ProveedorApi[]>(`${this.base}/proveedores`);
  }

  getProveedor(id: number): Observable<ProveedorApi> {
    return this.http.get<ProveedorApi>(`${this.base}/proveedores/${id}`);
  }

  buscarProveedores(nombre: string): Observable<ProveedorApi[]> {
    return this.http.get<ProveedorApi[]>(`${this.base}/proveedores/buscar`, {
      params: new HttpParams().set('nombre', nombre),
    });
  }

  getProveedoresActivos(): Observable<ProveedorApi[]> {
    return this.http.get<ProveedorApi[]>(`${this.base}/proveedores/activos`);
  }

  postProveedor(proveedor: ProveedorApi): Observable<ProveedorApi> {
    return this.http.post<ProveedorApi>(`${this.base}/proveedores`, proveedor);
  }

  putProveedor(id: number, proveedor: Partial<ProveedorApi>): Observable<ProveedorApi> {
    return this.http.put<ProveedorApi>(`${this.base}/proveedores/${id}`, proveedor);
  }

  deleteProveedor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/proveedores/${id}`);
  }

  // ── CLIENTES ──────────────────────────────────────────────────────────
  getClientes(): Observable<ClienteApi[]> {
    return this.http.get<ClienteApi[]>(`${this.base}/clientes`);
  }

  getCliente(id: number): Observable<ClienteApi> {
    return this.http.get<ClienteApi>(`${this.base}/clientes/${id}`);
  }

  buscarClientes(nombre: string): Observable<ClienteApi[]> {
    return this.http.get<ClienteApi[]>(`${this.base}/clientes/buscar`, {
      params: new HttpParams().set('nombre', nombre),
    });
  }

  getClientesPorRol(rolClienteId: number): Observable<ClienteApi[]> {
    return this.http.get<ClienteApi[]>(`${this.base}/clientes/rol/${rolClienteId}`);
  }

  postCliente(cliente: ClienteApi): Observable<ClienteApi> {
    return this.http.post<ClienteApi>(`${this.base}/clientes`, cliente);
  }

  putCliente(id: number, cliente: Partial<ClienteApi>): Observable<ClienteApi> {
    return this.http.put<ClienteApi>(`${this.base}/clientes/${id}`, cliente);
  }

  deleteCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/clientes/${id}`);
  }
}
