import { Component, OnInit } from '@angular/core';

export interface FacturaLinea {
  descripcion: string;
  cantidad: number;
  precioUnitario: number;
  iva: number;
}

export interface Factura {
  id: string;
  fecha: Date;
  clienteNombre: string | null;
  total: number;
  metodoPago: string | null;
  estado: 'pagada' | 'pendiente' | 'anulada';
}

export interface FacturaDetalle extends Factura {
  lineas: FacturaLinea[];
  subtotal: number;
  totalIva: number;
}

export interface FiltrosFactura {
  fechaDesde: Date | null;
  fechaHasta: Date | null;
  numero: string;
  cliente: string;
  estado: string;
}

@Component({
  selector: 'app-facturas',
  templateUrl: './facturas.page.html',
  styleUrls: ['./facturas.page.scss'],
  standalone: false,
})
export class FacturasPage implements OnInit {

  // ─── Datos ───────────────────────────────────────────────────────────────
  todasFacturas: Factura[] = [];
  facturasFiltradas: Factura[] = [];

  // ─── Filtros ─────────────────────────────────────────────────────────────
  filtros: FiltrosFactura = {
    fechaDesde: null,
    fechaHasta: null,
    numero: '',
    cliente: '',
    estado: '',
  };

  // ─── Modal detalle ────────────────────────────────────────────────────────
  isDetalleOpen = false;
  facturaDetalle: FacturaDetalle | null = null;
  anioActual = new Date().getFullYear();

  // ─── Paginación ──────────────────────────────────────────────────────────
  paginaActual = 1;
  porPagina = 4;

  // ─── KPI ─────────────────────────────────────────────────────────────────
  get totalFacturado(): number {
    return this.facturasFiltradas.reduce((acc, f) => acc + f.total, 0);
  }

  // ─── Paginación calculada ────────────────────────────────────────────────
  get totalPaginas(): number {
    return Math.max(1, Math.ceil(this.facturasFiltradas.length / this.porPagina));
  }

  get facturasPaginadas(): Factura[] {
    const inicio = (this.paginaActual - 1) * this.porPagina;
    return this.facturasFiltradas.slice(inicio, inicio + this.porPagina);
  }

  get rangoInicio(): number {
    return this.facturasFiltradas.length === 0 ? 0 : (this.paginaActual - 1) * this.porPagina + 1;
  }

  get rangoFin(): number {
    return Math.min(this.paginaActual * this.porPagina, this.facturasFiltradas.length);
  }

  get paginasVisibles(): number[] {
    const total = this.totalPaginas;
    const actual = this.paginaActual;
    if (total <= 5) return Array.from({ length: total }, (_, i) => i + 1);

    const paginas: number[] = [1];
    if (actual > 3) paginas.push(-1); // puntos
    for (let p = Math.max(2, actual - 1); p <= Math.min(total - 1, actual + 1); p++) {
      paginas.push(p);
    }
    if (actual < total - 2) paginas.push(-1); // puntos
    paginas.push(total);
    return paginas;
  }

  constructor() {}

  ngOnInit() {
    this.cargarDatosDemo();
    this.aplicarFiltros();
  }

  // ─── Demo data ───────────────────────────────────────────────────────────
  private cargarDatosDemo() {
    const metodos = ['Efectivo', 'Tarjeta', 'Transferencia', null];
    const estados: Array<'pagada' | 'pendiente' | 'anulada'> = ['pagada', 'pagada', 'anulada', 'pendiente'];
    const clientes = [null, 'Restaurante El Faro', 'María Gómez', 'Panadería San José', 'Supermercados Díaz'];

    this.todasFacturas = Array.from({ length: 128 }, (_, i) => {
      const idx = i % 4;
      const fecha = new Date('2023-10-24');
      fecha.setMinutes(fecha.getMinutes() - i * 70);
      return {
        id: `#FAC-${String(452 - i).padStart(5, '0')}`,
        fecha: new Date(fecha),
        clienteNombre: clientes[i % clientes.length],
        total: +(Math.random() * 250 + 10).toFixed(2),
        metodoPago: metodos[idx],
        estado: estados[idx],
      } as Factura;
    });
  }

  // ─── Filtrado ────────────────────────────────────────────────────────────
  aplicarFiltros() {
    this.facturasFiltradas = this.todasFacturas.filter(f => {
      if (this.filtros.fechaDesde && new Date(f.fecha) < this.filtros.fechaDesde) return false;
      if (this.filtros.fechaHasta && new Date(f.fecha) > this.filtros.fechaHasta) return false;
      if (this.filtros.numero && !f.id.toLowerCase().includes(this.filtros.numero.toLowerCase())) return false;
      if (this.filtros.cliente) {
        const nombre = f.clienteNombre?.toLowerCase() ?? '';
        if (!nombre.includes(this.filtros.cliente.toLowerCase())) return false;
      }
      if (this.filtros.estado && f.estado !== this.filtros.estado) return false;
      return true;
    });
    this.paginaActual = 1;
  }

  limpiarFiltros() {
    this.filtros = { fechaDesde: null, fechaHasta: null, numero: '', cliente: '', estado: '' };
    this.aplicarFiltros();
  }

  // ─── Datepickers (stubs) ─────────────────────────────────────────────────
  abrirDatepickerDesde() { /* TODO: integrar con ion-datetime o librería de fechas */ }
  abrirDatepickerHasta() { /* TODO: integrar con ion-datetime o librería de fechas */ }

  // ─── Paginación ──────────────────────────────────────────────────────────
  irPagina(p: number) {
    if (p < 1 || p > this.totalPaginas) return;
    this.paginaActual = p;
  }

  // ─── Acciones ────────────────────────────────────────────────────────────
  verDetalle(factura: Factura) {
    // Generar líneas de demo proporcionales al total
    const lineasDemo: FacturaLinea[] = this.generarLineasDemo(factura.total);
    const subtotal = +(lineasDemo.reduce((s, l) => s + l.cantidad * l.precioUnitario, 0)).toFixed(2);
    const totalIva = +(subtotal * 0.21).toFixed(2);

    this.facturaDetalle = {
      ...factura,
      lineas: lineasDemo,
      subtotal,
      totalIva,
    };
    this.isDetalleOpen = true;
  }

  cerrarDetalle() {
    this.isDetalleOpen = false;
    this.facturaDetalle = null;
  }

  imprimirFactura() {
    // Inyectar estilos de impresión temporalmente si no existen
    const id = 'factura-print-style';
    if (!document.getElementById(id)) {
      const style = document.createElement('style');
      style.id = id;
      style.media = 'print';
      style.textContent = `
        body > * { display: none !important; }
        ion-modal.modal-detalle-factura { display: block !important; position: fixed !important;
          inset: 0; z-index: 9999; background: white; }
        .factura-print-area { display: block !important; padding: 24px; }
        .btn-modal-accion, .btn-modal-cerrar, ion-buttons { display: none !important; }
      `;
      document.head.appendChild(style);
    }
    window.print();
  }

  descargarPDF(factura: Factura) {
    // En web, print-to-PDF es el estándar sin librerías externas
    this.imprimirFactura();
  }

  private generarLineasDemo(totalFinal: number): FacturaLinea[] {
    const productos = [
      'Pan artesano 500g', 'Leche entera 1L', 'Aceite oliva virgen 1L',
      'Jamón serrano 200g', 'Queso manchego 250g', 'Vino tinto Rioja 75cl',
      'Agua mineral 5L', 'Huevos camperos docena',
    ];
    const numLineas = Math.floor(Math.random() * 4) + 1;
    const baseTotal = +(totalFinal / 1.21).toFixed(2);
    const lineas: FacturaLinea[] = [];
    let acumulado = 0;

    for (let i = 0; i < numLineas; i++) {
      const precio = +(baseTotal / numLineas * (0.7 + Math.random() * 0.6)).toFixed(2);
      const cant = Math.floor(Math.random() * 3) + 1;
      lineas.push({
        descripcion: productos[Math.floor(Math.random() * productos.length)],
        cantidad: cant,
        precioUnitario: +(precio / cant).toFixed(2),
        iva: 21,
      });
      acumulado += precio;
    }
    return lineas;
  }

  // ─── Helpers ─────────────────────────────────────────────────────────────
  getIconMetodo(metodo: string): string {
    const mapa: Record<string, string> = {
      'Efectivo': 'cash-outline',
      'Tarjeta': 'card-outline',
      'Transferencia': 'business-outline',
    };
    return mapa[metodo] ?? 'help-circle-outline';
  }
}
