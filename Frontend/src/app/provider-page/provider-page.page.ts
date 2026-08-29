import { Component, OnInit } from '@angular/core';
import { Myservice } from '../service/myservice';

export type EstadoPedido = 'Recibido' | 'Pendiente' | 'En tránsito' | 'Cancelado';

export interface Proveedor {
  id: string;
  nombre: string;
  telefono?: string;
  email?: string;
  categoria: string;
  icono: string;
  color: string;
}

export interface Compra {
  id: string;
  proveedorId: string;
  proveedor: string;
  fecha: string;
  total: number;
  estado: EstadoPedido;
  concepto?: string;
}

@Component({
  selector: 'app-provider-page',
  templateUrl: './provider-page.page.html',
  styleUrls: ['./provider-page.page.scss'],
  standalone: false
})
export class ProviderPagePage implements OnInit {

  // ─── Proveedores ──────────────────────────────────────────────────────────
  proveedores: Proveedor[] = [
    { id: '1', nombre: 'Distribuciones Frutales S.L.', telefono: '912 345 678', categoria: 'Frutas y Verduras', icono: 'car-outline',    color: '#16a34a' },
    { id: '2', nombre: 'Panadería La Tradicional',     telefono: '911 223 344', categoria: 'Pan y Bollería',   icono: 'business-outline', color: '#d97706' },
    { id: '3', nombre: 'Lácteos del Norte S.A.',       email:    'pedidos@lacteoslnorte.es', categoria: 'Lácteos y Huevos', icono: 'search-outline', color: '#2563eb' },
  ];

  // ─── Compras / Pedidos ────────────────────────────────────────────────────
  compras: Compra[] = [
    { id: 'COMP-1042', proveedorId: '1', proveedor: 'Distribuciones Frutales S.L.', fecha: 'Hoy, 08:30',     total: 345.50, estado: 'Recibido'    },
    { id: 'COMP-1041', proveedorId: '3', proveedor: 'Lácteos del Norte S.A.',       fecha: 'Ayer, 16:45',    total: 128.90, estado: 'Pendiente'   },
    { id: 'COMP-1040', proveedorId: '4', proveedor: 'Bebidas y Refrescos S.L.',     fecha: '22 May 2024',    total: 560.00, estado: 'Recibido'    },
    { id: 'COMP-1039', proveedorId: '2', proveedor: 'Panadería La Tradicional',    fecha: '21 May 2024',    total:  45.20, estado: 'Recibido'    },
    { id: 'COMP-1038', proveedorId: '1', proveedor: 'Distribuciones Frutales S.L.', fecha: '20 May 2024',    total: 210.00, estado: 'En tránsito' },
    { id: 'COMP-1037', proveedorId: '3', proveedor: 'Lácteos del Norte S.A.',       fecha: '18 May 2024',    total:  98.75, estado: 'Cancelado'   },
  ];

  // ─── Filtros compras ──────────────────────────────────────────────────────
  busquedaCompra: string = '';
  estadoFiltro: string = '';

  // ─── Modal Registrar Compra ───────────────────────────────────────────────
  isModalOpen: boolean = false;
  isVerTodosOpen: boolean = false;

  form: any = {
    proveedorId: '',
    concepto: '',
    total: null,
    estado: 'Pendiente',
    fecha: new Date().toLocaleDateString('es-ES'),
  };

  // Legacy compat
  filtroProveedores: any[] = [];
  filtroSegmento: number = 0;
  selectedCategory: any[] = [];
  searchText: string = '';
  isChangeToogle: boolean = false;

  constructor(private myService: Myservice) {}

  ngOnInit() { this.getAllData(); }
  ionViewDidEnter() { this.getAllData(); }

  getAllData() {
    this.myService.getProveedores().subscribe({
      next: (res: any) => { this.filtroProveedores = res; }
    });
    this.myService.getCategorias().subscribe({
      next: (res: any) => { this.selectedCategory = res; }
    });
  }

  // ─── Filtrado compras ─────────────────────────────────────────────────────
  get comprasFiltradas(): Compra[] {
    return this.compras.filter(c => {
      const q = this.busquedaCompra.toLowerCase();
      const porTexto = !q ||
        c.id.toLowerCase().includes(q) ||
        c.proveedor.toLowerCase().includes(q);
      const porEstado = !this.estadoFiltro || c.estado === this.estadoFiltro;
      return porTexto && porEstado;
    });
  }

  // ─── Modal Registrar Compra ───────────────────────────────────────────────
  abrirModalCompra() {
    this.form = { proveedorId: '', concepto: '', total: null, estado: 'Pendiente', fecha: new Date().toLocaleDateString('es-ES') };
    this.isModalOpen = true;
  }

  guardarCompra() {
    if (!this.form.proveedorId || !this.form.total) return;
    const prov = this.proveedores.find(p => p.id === this.form.proveedorId);
    const nuevo: Compra = {
      id: `COMP-${1000 + this.compras.length + 1}`,
      proveedorId: this.form.proveedorId,
      proveedor:   prov?.nombre || 'Desconocido',
      fecha:       'Hoy',
      total:       +this.form.total,
      estado:      this.form.estado,
      concepto:    this.form.concepto,
    };
    this.compras.unshift(nuevo);
    this.isModalOpen = false;
  }

  cerrarModal() { this.isModalOpen = false; }

  // ─── Helpers ──────────────────────────────────────────────────────────────
  getEstadoColor(estado: string): string {
    const mapa: Record<string, string> = {
      'Recibido':    '#16a34a',
      'Pendiente':   '#d97706',
      'En tránsito': '#2563eb',
      'Cancelado':   '#e53935',
    };
    return mapa[estado] ?? '#6b7280';
  }

  getEstadoIcono(estado: string): string {
    const mapa: Record<string, string> = {
      'Recibido':    'checkmark-circle-outline',
      'Pendiente':   'time-outline',
      'En tránsito': 'car-outline',
      'Cancelado':   'close-circle-outline',
    };
    return mapa[estado] ?? 'ellipse-outline';
  }

  // Legacy compat
  filterProveedor(event: any) { this.busquedaCompra = event.target?.value || ''; }
  filterProductsBySegment(event: any) { this.filtroSegmento = event.detail?.value ?? 0; }
  aplicarFiltros(t: string, id: number) {}
}
