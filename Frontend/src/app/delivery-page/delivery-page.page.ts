import { Component, OnInit } from '@angular/core';
import { Myservice } from '../service/myservice';
import { BarcodeScanner } from '@capacitor-mlkit/barcode-scanning';
import { AlertControl } from '../service/alert-control';
import * as QRCode from 'qrcode';

@Component({
  selector: 'app-delivery-page',
  templateUrl: './delivery-page.page.html',
  styleUrls: ['./delivery-page.page.scss'],
  standalone: false
})
export class DeliveryPagePage implements OnInit {

  // ─── Datos ────────────────────────────────────────────────────────────────
  pedidos: any[]          = [];
  ordenesEntrega: any[]   = [];
  centrosTrabajo: any[]   = [];
  clientes: any[]         = [];
  conductores: any[]      = [];
  detallePedidos: any[]   = [];

  filtroOrdenesEntrega: any[]  = [];
  filtroDetallePedidos: any[]  = [];
  pedidoDetalleData: any       = null;

  // ─── Filtros ──────────────────────────────────────────────────────────────
  searchText: string    = '';
  estadoFiltro: string  = '';
  dropdownEstadoOpen    = false;

  estados: string[] = ['Pendiente', 'En proceso', 'Enviado', 'Entregado', 'Cancelado'];

  // ─── Paginación ───────────────────────────────────────────────────────────
  paginaActual: number = 1;
  pageSize: number     = 8;

  // ─── Modales ──────────────────────────────────────────────────────────────
  isModalOpen: boolean        = false;
  isModalOpenUpdate: boolean  = false;
  isModalOpenDetalle: boolean = false;
  isModalOpenNuevoPedido: boolean = false;
  isModalQRVisible: boolean   = false;

  // ─── Nuevo Pedido Data ────────────────────────────────────────────────────
  clienteSeleccionadoId: string = '';
  clienteSeleccionado: any    = null;
  
  searchProductoText: string  = '';
  productosBuscados: any[]    = [];
  productosSeleccionados: any[] = [];
  
  qrCodeDataUrl: string       = '';
  pedidoReciente: any         = null;


  // ─── Campos de edición ────────────────────────────────────────────────────
  idPedidoData: string  = '';
  cifCliente: string    = '';
  idCentro: string      = '';
  idEmpleado: string    = '';
  fechaPedido: string   = '';
  estado: string        = '';

  // ─── Legacy compat ────────────────────────────────────────────────────────
  isChangeToogle: boolean  = false;
  pedidosFiltrados: any[]  = [];
  scannedId: string        = '';
  qrcodeImage: string      = '';

  // ─── KPIs ─────────────────────────────────────────────────────────────────
  kpis: any[] = [];

  constructor(
    private myService: Myservice,
    private alert: AlertControl
  ) {}

  ngOnInit()        { this.getAllData(); }
  ionViewDidEnter() { this.getAllData(); }

  // ─── Carga de datos ───────────────────────────────────────────────────────
  getAllData() {
    this.myService.getPedidos().subscribe({
      next: (res: any) => {
        this.pedidos         = res;
        this.pedidosFiltrados = res;
        this._calcularKpis();
        this.filtrarPedidos();
      }
    });
    this.myService.getOrdenesEntrega().subscribe({
      next: (res: any) => { this.ordenesEntrega = res; this.filtroOrdenesEntrega = res; }
    });
    this.myService.getCentrosTrabajo().subscribe({
      next: (res: any) => { this.centrosTrabajo = res; }
    });
    this.myService.getClientes().subscribe({
      next: (res: any) => { this.clientes = res; }
    });
    this.myService.getDetallePedidos().subscribe({
      next: (res: any) => { this.detallePedidos = res; this.filtroDetallePedidos = res; }
    });
    // Fetch products for order creation
    this.myService.getProductos().subscribe({
      next: (res: any) => { this.productosBuscados = res; }
    });
  }

  // ─── KPIs ─────────────────────────────────────────────────────────────────
  private _calcularKpis() {
    const total      = this.pedidos.length;
    const pendiente  = this.pedidos.filter(p => p.estado === 'Pendiente').length;
    const enproceso  = this.pedidos.filter(p => p.estado === 'En proceso' || p.estado === 'Enviado').length;
    const entregado  = this.pedidos.filter(p => p.estado === 'Entregado').length;

    this.kpis = [
      { label: 'Total Pedidos',    valor: total,     icono: 'cube-outline',            color: '#2563eb' },
      { label: 'Pendientes',       valor: pendiente,  icono: 'time-outline',            color: '#d97706' },
      { label: 'En Tránsito',      valor: enproceso,  icono: 'car-outline',             color: '#7c3aed' },
      { label: 'Entregados',       valor: entregado,  icono: 'checkmark-circle-outline', color: '#16a34a' },
    ];
  }

  // ─── Filtrado y paginación ────────────────────────────────────────────────
  filtrarPedidos() {
    const texto = this.searchText.toLowerCase().trim();
    this.pedidosFiltrados = this.pedidos.filter(p =>
      (!texto || p.idPedido?.toLowerCase().includes(texto) ||
        p.cliente?.nombre?.toLowerCase().includes(texto) ||
        p.cliente?.email?.toLowerCase().includes(texto) ||
        p.cliente?.cifCliente?.toLowerCase().includes(texto)) &&
      (!this.estadoFiltro || p.estado === this.estadoFiltro)
    );
    this.paginaActual = 1;
    this.dropdownEstadoOpen = false;
  }

  setEstadoFiltro(estado: string) {
    this.estadoFiltro = estado;
    this.filtrarPedidos();
  }

  get totalPaginas(): number {
    return Math.max(1, Math.ceil(this.pedidosFiltrados.length / this.pageSize));
  }

  get pedidosPagina(): any[] {
    const start = (this.paginaActual - 1) * this.pageSize;
    return this.pedidosFiltrados.slice(start, start + this.pageSize);
  }

  get paginasVisibles(): number[] {
    const t = this.totalPaginas, c = this.paginaActual;
    const s = Math.max(1, c - 1), e = Math.min(t, s + 2);
    return Array.from({ length: e - s + 1 }, (_, i) => s + i);
  }

  irPagina(n: number) {
    if (n >= 1 && n <= this.totalPaginas) this.paginaActual = n;
  }

  // ─── Nuevo pedido ─────────────────────────────────────────────
  abrirModalNuevoPedido() {
    this.clienteSeleccionadoId = '';
    this.clienteSeleccionado = null;
    this.searchProductoText = '';
    this.productosSeleccionados = [];
    this.qrCodeDataUrl = '';
    this.pedidoReciente = null;
    this.isModalOpenNuevoPedido = true;
  }

  cerrarModalNuevoPedido() {
    this.isModalOpenNuevoPedido = false;
  }

  onClienteChange() {
    this.clienteSeleccionado = this.clientes.find(c => String(c.id) === String(this.clienteSeleccionadoId)) || null;
  }

  buscarProductoParaPedido() {
    const texto = this.searchProductoText.toLowerCase().trim();
    this.myService.getProductos().subscribe({
      next: (res: any) => {
        if (!texto) {
          this.productosBuscados = res;
        } else {
          this.productosBuscados = res.filter((p: any) => 
            p.nombre && p.nombre.toLowerCase().includes(texto)
          );
        }
      }
    });
  }

  agregarProductoAlPedido(producto: any) {
    const existe = this.productosSeleccionados.find(p => p.producto.id === producto.id);
    if (existe) {
      existe.cantidad++;
    } else {
      this.productosSeleccionados.push({ producto, cantidad: 1 });
    }
    this.alert.alertControl('Producto Añadido', `${producto.nombre} añadido al pedido.`);
  }

  removerProductoDelPedido(index: number) {
    this.productosSeleccionados.splice(index, 1);
  }

  calcularTotalPedido(): number {
    return this.productosSeleccionados.reduce((total, p) => total + (p.producto.precio * p.cantidad), 0);
  }

  async crearNuevoPedido() {
    if (!this.clienteSeleccionado) {
      this.alert.alertControl('Error', 'Debes seleccionar un cliente.');
      return;
    }
    if (this.productosSeleccionados.length === 0) {
      this.alert.alertControl('Error', 'El pedido debe tener al menos un producto.');
      return;
    }

    const nuevoIdPedido = `PED-${1000 + Math.floor(Math.random() * 9000)}`;
    const nuevoPedido = {
      idPedido: nuevoIdPedido,
      estado: 'Pendiente',
      fechaPedido: new Date().toISOString().split('T')[0],
      cliente: this.clienteSeleccionado,
      empleado: { nombre: 'Empleado', apellido: 'Actual', centroTrabajo: { nombreCentro: 'Principal' } },
      centroTrabajo: { idCentro: '1', nombreCentro: 'Principal' },
      total: this.calcularTotalPedido(),
      productos: this.productosSeleccionados.map(p => ({
        productoId: p.producto.id,
        nombre: p.producto.nombre,
        cantidad: p.cantidad,
        precio: p.producto.precio
      }))
    };

    this.myService.postPedidos(nuevoPedido).subscribe({
      next: async (res: any) => {
        // Pedido guardado. Actualizar vista.
        this.getAllData();
        this.pedidoReciente = res;
        
        // Generar QR
        const qrData = JSON.stringify({
          idPedido: res.idPedido,
          clienteId: this.clienteSeleccionado.id,
          clienteCif: this.clienteSeleccionado.cifCliente || '',
          clienteNombre: this.clienteSeleccionado.nombre,
          clienteTelefono: this.clienteSeleccionado.telefono || ''
        });

        try {
          this.qrCodeDataUrl = await QRCode.toDataURL(qrData, { width: 200, margin: 2 });
          this.isModalOpenNuevoPedido = false;
          this.isModalQRVisible = true;
        } catch (err) {
          console.error(err);
          this.alert.alertControl('Éxito', 'Pedido creado, pero falló la generación del QR.');
        }
      }
    });
  }

  cerrarModalQR() {
    this.isModalQRVisible = false;
    this.qrCodeDataUrl = '';
    this.pedidoReciente = null;
  }

  // ─── Modales existentes (mantenidos) ─────────────────────────────────────
  openModal()         { this.isModalOpen = true; }
  closeModal()        { this.isModalOpen = false; }
  openModalUpdate()   { this.isModalOpenUpdate = true; }
  closeModalUpdate()  { this.isModalOpenUpdate = false; }
  openModalDetalle()  { this.isModalOpenDetalle = true; }
  closeModalDetalle() { this.isModalOpenDetalle = false; }

  openModalOrdenesEntrega(id: string) {
    this.idPedidoData = id;
    this.filtroOrdenesEntrega = this.ordenesEntrega.filter(o => o.idPedido === id);
    this.openModal();
  }

  openModalDetallePedido(id: string) {
    this.idPedidoData = id;
    const p = this.pedidos.find(x => String(x.idPedido) === String(id));
    this.pedidoDetalleData = p; // Store to use in printQR
    if (p) {
      this.fechaPedido = p.fechaPedido;
      this.estado      = p.estado;
      this.idCentro    = p.centroTrabajo?.nombreCentro || p.idCentro;
      const emp        = p.empleado;
      this.idEmpleado  = emp ? `${emp.nombre} ${emp.apellido}` : p.idEmpleado;
    }
    this.filtroDetallePedidos = this.detallePedidos.filter(d => d.idPedido === id);
    this.openModalDetalle();
  }

  isUpdatePedido(pedido: any) {
    this.idPedidoData = pedido.idPedido;
    this.cifCliente   = pedido.cifCliente || pedido.cliente?.cifCliente || '';
    this.idCentro     = pedido.idCentro   || pedido.centroTrabajo?.idCentro || '';
    this.idEmpleado   = pedido.idEmpleado || '';
    this.fechaPedido  = pedido.fechaPedido;
    this.estado       = pedido.estado;
    this.openModalUpdate();
  }

  async onUpdate() {
    const pedidoUpdate = { cifCliente: this.cifCliente, idCentro: this.idCentro, estado: this.estado };
    await this.updatePedido(this.idPedidoData, pedidoUpdate);
    this.closeModalUpdate();
  }

  async updatePedido(id: string, data: any) {
    const ok = await this.alert.alertControl('Actualizar Pedido', '¿Seguro que quieres actualizar este pedido?');
    if (ok) {
      // Actualización local en mock
      const idx = this.pedidos.findIndex(p => p.idPedido === id);
      if (idx !== -1) { this.pedidos[idx].estado = data.estado; this.filtrarPedidos(); }
      this.myService.putPedidos(id, data).subscribe({ next: () => this.getAllData() });
    }
  }

  async deletePedido(id: string) {
    const ok = await this.alert.alertControl('Eliminar Pedido', '¿Seguro que quieres eliminar este pedido?');
    if (ok) {
      this.pedidos = this.pedidos.filter(p => p.idPedido !== id);
      this.filtrarPedidos();
      this.myService.deletePedidos(id).subscribe({ next: () => this.getAllData() });
    }
  }

  // ─── Scanner QR ───────────────────────────────────────────────────────────
  async scanPedido() {
    try {
      const status = await BarcodeScanner.checkPermissions();
      if (status.camera !== 'granted') {
        const req = await BarcodeScanner.requestPermissions();
        if (req.camera !== 'granted') { console.error('Camera permission denied'); return; }
      }
      const { barcodes } = await BarcodeScanner.scan();
      if (barcodes.length > 0) {
        this.scannedId = barcodes[0].displayValue;
        this.trackPedido(this.scannedId);
      }
    } catch (e) {
      console.warn('BarcodeScanner no disponible en web:', e);
    }
  }

  trackPedido(id: string) {
    const p = this.pedidos.find(x => String(x.idPedido) === String(id));
    if (p) { this.openModalDetallePedido(p.idPedido); }
    else    { this.alert.alertControl('No encontrado', `El pedido ${id} no existe.`); }
  }

  // ─── QR ───────────────────────────────────────────────────────────────────
  async generateQR() {
    const p = this.pedidoDetalleData;
    const qrData = p ? JSON.stringify({
      idPedido: p.idPedido,
      clienteId: p.cliente?.id || '',
      clienteCif: p.cliente?.cifCliente || '',
      clienteNombre: p.cliente?.nombre || '',
      clienteTelefono: p.cliente?.telefono || ''
    }) : this.idPedidoData;

    try {
      this.qrcodeImage = await QRCode.toDataURL(qrData, { width: 200, margin: 2 });
      this.printQR();
    } catch (err) {
      console.error('Error al generar QR:', err);
    }
  }

  printQR() {
    setTimeout(() => {
      const p = this.pedidoDetalleData;
      const cliente = p?.cliente;
      const html = `<html><head><title>Ticket Entrega - ${this.idPedidoData}</title><style>
        body{font-family:Arial,sans-serif;display:flex;justify-content:center;}
        .ticket{width:80mm;padding:10mm;text-align:left;border:1px dashed #333;margin-top:5mm;}
        .qr-img{width:45mm;height:45mm;display:block;margin:0 auto 5mm;}
        h2{font-size:22px;margin-bottom:2mm;text-align:center;} 
        p{font-size:14px;color:#333;margin-bottom:1mm;line-height:1.4;}
        .sep{border-top:1px dashed #999;margin:3mm 0;}
        @media print{@page{size:80mm auto;margin:0;}}
      </style></head><body><div class="ticket">
        <h2 style="margin: 0; padding-bottom: 5mm; border-bottom: 2px dashed #000; text-align: center;">TICKET ENTREGA</h2>
        <img src="${this.qrcodeImage}" class="qr-img" alt="QR" style="margin-top: 5mm;" />
        <h2 style="font-size: 18px;">ID: ${this.idPedidoData}</h2>
        <p><strong>Fecha Pedido:</strong> ${this.fechaPedido ? new Date(this.fechaPedido).toLocaleDateString() : 'N/A'}</p>
        
        <div class="sep"></div>
        <p><strong>Cliente:</strong> ${cliente?.nombre || 'N/A'}</p>
        <p><strong>Dirección:</strong> ${cliente?.direccion || 'Sin dirección registrada'}</p>
        <p><strong>Teléfono:</strong> ${cliente?.telefono || 'N/A'}</p>

        <div class="sep"></div>
        <p><strong>Origen:</strong> ${this.idCentro}</p>
        <p><strong>Repartidor:</strong> ${this.idEmpleado}</p>
      </div></body></html>`;
      const iframe = document.createElement('iframe');
      iframe.style.position = 'absolute'; iframe.style.top = '-1000px';
      document.body.appendChild(iframe);
      const doc = iframe.contentWindow?.document;
      if (doc) {
        doc.open(); doc.write(html); doc.close();
        setTimeout(() => { iframe.contentWindow?.print(); document.body.removeChild(iframe); }, 500);
      }
    }, 1000);
  }

  // ─── Helpers visuales ────────────────────────────────────────────────────
  getStatusHex(status: string): string {
    const mapa: Record<string, string> = {
      'pendiente':   '#d97706',
      'en proceso':  '#2563eb',
      'enviado':     '#7c3aed',
      'entregado':   '#16a34a',
      'cancelado':   '#e53935',
      'en reparto':  '#2563eb',
    };
    return mapa[status?.toLowerCase()] ?? '#6b7280';
  }

  /** Compat: devuelve nombre de color Ionic (se usa en algún sitio antiguo) */
  getStatusColor(status: string): string {
    const mapa: Record<string, string> = {
      'pendiente': 'warning', 'en proceso': 'primary',
      'enviado': 'tertiary',  'entregado': 'success', 'cancelado': 'danger',
    };
    return mapa[status?.toLowerCase()] ?? 'medium';
  }
}
