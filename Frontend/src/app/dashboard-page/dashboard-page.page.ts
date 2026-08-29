import { Component, OnInit } from '@angular/core';
import { Myservice } from '../service/myservice';

interface KpiCard {
  label: string;
  valor: number;
  simbolo: string;
  icono: string;
  color: string;
  descripcion: string;
  tendencia?: string;
  tendenciaPositiva?: boolean;
}

interface AccionRapida {
  label: string;
  icono: string;
  ruta: string;
  color: string;
}

interface Actividad {
  descripcion: string;
  hora: string;
  icono: string;
  color: string;
}

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.page.html',
  styleUrls: ['./dashboard-page.page.scss'],
  standalone: false,
})
export class DashboardPagePage implements OnInit {

  // ─── KPIs ─────────────────────────────────────────────────────────────────
  productCount: number = 0;
  clientCount: number = 0;
  providerCount: number = 0;
  pendingOrdersCount: number = 0;
  totalPedidos: number = 0;
  pedidosEntregados: number = 0;
  ventasTotales: number = 1000;
  isLoading: boolean = true;

  fecha: string = '';
  saludo: string = '';

  kpis: KpiCard[] = [];

  accionesRapidas: AccionRapida[] = [
    { label: 'TPV', icono: 'storefront-outline', ruta: '/dashboard-control/tpv', color: '#1a7a4a' },
    { label: 'Pedidos', icono: 'car-outline', ruta: '/dashboard-control/delivery-page', color: '#7c3aed' },
    { label: 'Caja', icono: 'cash-outline', ruta: '/dashboard-control/caja', color: '#0891b2' },
  ];

  actividadReciente: Actividad[] = [
    { descripcion: 'Nuevo pedido registrado — PED-1042', hora: 'Hace 5 min', icono: 'cube-outline', color: '#2563eb' },
    { descripcion: 'Stock bajo: Pan de Masa Madre (0 ud)', hora: 'Hace 18 min', icono: 'warning-outline', color: '#d97706' },
    { descripcion: 'Compra recibida — Frutales S.L.', hora: 'Hace 1 h', icono: 'checkmark-circle-outline', color: '#16a34a' },
    { descripcion: 'Pedido entregado — PED-1039', hora: 'Hace 2 h', icono: 'car-outline', color: '#7c3aed' },
    { descripcion: 'Apertura de Caja — €150 fondo inicial', hora: 'Hace 3 h', icono: 'cash-outline', color: '#0891b2' },
  ];

  constructor(private myService: Myservice) { }

  ngOnInit() {
    this._setSaludo();
    this.loadDashboardData();
  }

  private _setSaludo() {
    const hora = new Date().getHours();
    this.saludo = hora < 12 ? 'Buenos días' : hora < 20 ? 'Buenas tardes' : 'Buenas noches';
    this.fecha = new Date().toLocaleDateString('es-ES', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });
  }

  loadDashboardData() {
    this.isLoading = true;

    this.myService.getProductos().subscribe({
      next: (res: any) => {
        this.productCount = res.length;
        this._actualizarKpis();
      }
    });

    this.myService.getClientes().subscribe({
      next: (res: any) => {
        this.clientCount = res.length;
        this._actualizarKpis();
      }
    });

    this.myService.getProveedores().subscribe({
      next: (res: any) => {
        this.providerCount = res.length;
        this._actualizarKpis();
      }
    });

    this.myService.getPedidos().subscribe({
      next: (res: any) => {
        this.totalPedidos = res.length;
        this.pedidosEntregados = res.filter((p: any) => p.estado === 'Entregado').length;
        this.pendingOrdersCount = res.filter((p: any) => p.estado === 'Pendiente' || p.estadoPedido === 'Pendiente').length;
        this.isLoading = false;
        this._actualizarKpis();
      },
      error: () => { this.isLoading = false; }
    });
  }

  private _actualizarKpis() {
    this.kpis = [
      {
        label: 'Productos', valor: this.productCount, simbolo: '', icono: 'cube-outline',
        color: '#2563eb', descripcion: 'En catálogo', tendencia: '+2 este mes', tendenciaPositiva: true,
      },
      {
        label: 'Proveedores', valor: this.providerCount, simbolo: '', icono: 'business-outline',
        color: '#d97706', descripcion: 'Activos',
      },
      {
        label: 'Pedidos Pendientes', valor: this.pendingOrdersCount, simbolo: '', icono: 'time-outline',
        color: '#e53935', descripcion: 'Por procesar', tendencia: this.pendingOrdersCount > 0 ? 'Requieren atención' : 'Al día',
        tendenciaPositiva: this.pendingOrdersCount === 0,
      },
      {
        label: 'Ventas', valor: this.ventasTotales, simbolo: "€", icono: 'cash-outline',
        color: '#035a31ff', descripcion: 'Cantidad total de ventas',
      },
    ];
  }

  get tasaEntrega(): number {
    if (!this.totalPedidos) return 0;
    return Math.round((this.pedidosEntregados / this.totalPedidos) * 100);
  }
}
