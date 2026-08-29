import { Component, OnInit, OnDestroy } from '@angular/core';
import { AlertController } from '@ionic/angular';
import { Myservice, SesionTPV, LineaTicketTPV } from '../service/myservice';
import { Subscription } from 'rxjs';

interface Producto {
  id: number;
  codigo: string;
  nombre: string;
  categoria: string;
  precio: number;          // precio unitario o precio/kg si esPorPeso
  imagen: string;
  agotado?: boolean;
  esPorPeso?: boolean;     // true → se vende por kg
}

@Component({
  selector: 'app-tpv',
  templateUrl: './tpv.page.html',
  styleUrls: ['./tpv.page.scss'],
  standalone: false
})
export class TPVPage implements OnInit, OnDestroy {

  // ─── Pestañas panel izquierdo ────────────────────────────────────────────────
  pestanaActiva: 'catalogo' | 'manual' = 'catalogo';

  // ─── Sesiones (Multi-Usuario desde Myservice) ──────────────────────────────
  sesiones: SesionTPV[] = [];
  sesionActivaIndex: number = 0;
  private sub: Subscription = new Subscription();

  // ─── Catálogo ────────────────────────────────────────────────────────────────
  categorias: string[] = ['Todos', 'Frutas y Verduras', 'Lácteos', 'Panadería', 'Bebidas', 'Limpieza'];
  categoriaActiva: string = 'Todos';
  busqueda: string = '';

  productos: Producto[] = [
    { id: 1, codigo: '001', nombre: 'Aguacate Hass Premium', categoria: 'Frutas y Verduras', precio: 4.99, imagen: 'https://images.unsplash.com/photo-1601039641847-7857b994d704?w=120&q=80' },
    { id: 2, codigo: '002', nombre: 'Tomate Rama', categoria: 'Frutas y Verduras', precio: 2.45, imagen: 'https://images.unsplash.com/photo-1546094096-0df4bcaaa337?w=120&q=80' },
    { id: 3, codigo: '003', nombre: 'Pan Rústico Artesanal', categoria: 'Panadería', precio: 1.80, imagen: 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=120&q=80' },
    { id: 4, codigo: '004', nombre: 'Leche Entera', categoria: 'Lácteos', precio: 1.25, imagen: 'https://images.unsplash.com/photo-1550583724-b2692b85b150?w=120&q=80' },
    { id: 5, codigo: '005', nombre: 'Plátano de Canarias', categoria: 'Frutas y Verduras', precio: 2.10, imagen: 'https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=120&q=80' },
    { id: 6, codigo: '006', nombre: 'Huevos Camperos', categoria: 'Lácteos', precio: 3.50, imagen: 'https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?w=120&q=80', agotado: true },
    { id: 7, codigo: '007', nombre: 'Agua Mineral 1.5L', categoria: 'Bebidas', precio: 0.65, imagen: 'https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=120&q=80' },
    { id: 8, codigo: '008', nombre: 'Zumo de Naranja', categoria: 'Bebidas', precio: 2.30, imagen: 'https://images.unsplash.com/photo-1621506289937-a8e4df240d0b?w=120&q=80' },
    { id: 9, codigo: '009', nombre: 'Yogur Natural', categoria: 'Lácteos', precio: 0.89, imagen: 'https://images.unsplash.com/photo-1488477181946-6428a0291777?w=120&q=80' },
    { id: 10, codigo: '010', nombre: 'Detergente Líquido', categoria: 'Limpieza', precio: 4.20, imagen: 'https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=120&q=80' },
    { id: 11, codigo: '011', nombre: 'Barra de Pan', categoria: 'Panadería', precio: 0.90, imagen: 'https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=120&q=80' },
    { id: 12, codigo: '012', nombre: 'Fregasuelos', categoria: 'Limpieza', precio: 2.99, imagen: 'https://images.unsplash.com/photo-1563453392212-326f5e854473?w=120&q=80' },
  ];

  // ─── Variables Globales Ticket ───────────────────────────────────────────────
  IVA_PORCENTAJE: number = 10;

  // ─── Entrada Manual ──────────────────────────────────────────────────────────
  modoEntrada: 'codigo' | 'cantidad' = 'codigo';
  valorEntrada: string = '';
  errorEntrada: string = '';
  productoEncontrado: Producto | null = null;

  // Producto pendiente de confirmar cantidad/peso (flujo de dos pasos)
  productoPendiente: Producto | null = null;

  // ─── Modal de Peso (catálogo F&V) ────────────────────────────────────────────
  modalPesoOpen = false;
  modalPesoProducto: Producto | null = null;
  modalPesoKg: string = '';           // kg introducidos en el modal
  modalPesoErrorEntrada: string = '';

  get modalPesoTotal(): number {
    if (!this.modalPesoProducto) return 0;
    const kg = parseFloat(this.modalPesoKg.replace(',', '.')) || 0;
    return +(kg * this.modalPesoProducto.precio).toFixed(2);
  }

  constructor(
    private myservice: Myservice,
    private alertController: AlertController
  ) { }

  ngOnInit() {
    this.sub = this.myservice.sesionesTPV$.subscribe(sesiones => {
      this.sesiones = sesiones;
      if (this.sesionActivaIndex >= this.sesiones.length) {
        this.sesionActivaIndex = Math.max(0, this.sesiones.length - 1);
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  // ─── Gestión de Sesiones ─────────────────────────────────────────────────────

  get sesionActiva(): SesionTPV | null {
    if (this.sesiones.length === 0) return null;
    return this.sesiones[this.sesionActivaIndex];
  }

  seleccionarSesion(index: number) {
    this.sesionActivaIndex = index;
  }

  async nuevaSesionPrompt() {
    const alert = await this.alertController.create({
      header: 'Abrir nueva sesión',
      message: 'Las sesiones se crean automáticamente al abrir un turno de caja. Por favor, ve a la pestaña "Caja" y realiza la Apertura.',
      buttons: ['Entendido']
    });
    await alert.present();
  }

  cerrarSesionActual() {
    if (this.sesiones.length === 0) return;
    const sesion = this.sesionActiva!;

    if (sesion.lineasTicket.length > 0) {
      alert('No puedes cerrar la sesión porque tiene un ticket pendiente. Cóbralo o anúlalo primero.');
      return;
    }

    alert('Para cerrar la sesión, debes ir a la pestaña "Caja" y hacer el Arqueo (Cierre de Caja).');
  }

  private _guardarSesiones() {
    this.myservice.actualizarSesionTPV(this.sesiones);
  }

  // ─── Catálogo ────────────────────────────────────────────────────────────────
  get productosFiltrados(): Producto[] {
    return this.productos.filter(p => {
      const porCategoria = this.categoriaActiva === 'Todos' || p.categoria === this.categoriaActiva;
      const porBusqueda = p.nombre.toLowerCase().includes(this.busqueda.toLowerCase());
      return porCategoria && porBusqueda;
    });
  }

  seleccionarCategoria(cat: string) {
    this.categoriaActiva = cat;
  }

  // ─── Carrito (Actúa sobre la sesión activa) ──────────────────────────────────
  agregarAlTicket(producto: Producto, cantidad: number = 1) {
    if (producto.agotado || !this.sesionActiva) return;

    // Frutas y Verduras → abrir modal de peso en vez de añadir directamente
    if (producto.categoria === 'Frutas y Verduras') {
      this.abrirModalPeso(producto);
      return;
    }

    const lineas = this.sesionActiva.lineasTicket;
    const linea = lineas.find(l => l.producto.id === producto.id && !l.esPorPeso);
    if (linea) {
      linea.cantidad += cantidad;
    } else {
      lineas.push({ producto, cantidad });
    }
    this._guardarSesiones();
  }

  // ─── Modal Peso ──────────────────────────────────────────────────────────────
  abrirModalPeso(producto: Producto) {
    this.modalPesoProducto = producto;
    this.modalPesoKg = '';
    this.modalPesoErrorEntrada = '';
    this.modalPesoOpen = true;
  }

  cerrarModalPeso() {
    this.modalPesoOpen = false;
    this.modalPesoProducto = null;
    this.modalPesoKg = '';
    this.modalPesoErrorEntrada = '';
  }

  confirmarModalPeso() {
    const kg = parseFloat(this.modalPesoKg.replace(',', '.'));
    if (!this.modalPesoKg || isNaN(kg) || kg <= 0) {
      this.modalPesoErrorEntrada = 'Introduce el peso en kg';
      return;
    }
    if (!this.modalPesoProducto) return;

    this.agregarAlTicketPorPeso(
      this.modalPesoProducto.nombre,
      this.modalPesoProducto.precio,
      kg,
      this.modalPesoProducto.imagen
    );
    this.cerrarModalPeso();
  }

  pulsarTeclaPeso(tecla: string) {
    this.modalPesoErrorEntrada = '';
    if (tecla === ',' && this.modalPesoKg.includes(',')) return;
    if (this.modalPesoKg.length >= 7) return;
    this.modalPesoKg += tecla;
  }

  borrarUltimoPeso() {
    this.modalPesoErrorEntrada = '';
    this.modalPesoKg = this.modalPesoKg.slice(0, -1);
  }

  agregarAlTicketPorPeso(nombre: string, precioKg: number, kg: number, imagen?: string) {
    if (!this.sesionActiva) return;
    const precioTotal = +(precioKg * kg).toFixed(2);
    const productoVirtual: Producto = {
      id: Date.now(),
      codigo: 'PESO',
      nombre: nombre || 'Producto a granel',
      categoria: 'Frutas y Verduras',
      precio: precioTotal,
      imagen: imagen || 'https://images.unsplash.com/photo-1542838132-92c53300491e?w=120&q=80',
      esPorPeso: true
    };
    const linea: LineaTicketTPV = {
      producto: productoVirtual,
      cantidad: 1,
      esPorPeso: true,
      kg,
      precioKg
    };
    this.sesionActiva.lineasTicket.push(linea);
    this._guardarSesiones();
  }

  aumentarCantidad(linea: LineaTicketTPV) {
    linea.cantidad++;
    this._guardarSesiones();
  }

  reducirCantidad(linea: LineaTicketTPV) {
    if (linea.cantidad > 1) {
      linea.cantidad--;
    } else {
      this.eliminarLinea(linea);
    }
    this._guardarSesiones();
  }

  eliminarLinea(linea: LineaTicketTPV) {
    if (!this.sesionActiva) return;
    this.sesionActiva.lineasTicket = this.sesionActiva.lineasTicket.filter(l => l !== linea);
    this._guardarSesiones();
  }

  estaEnTicket(producto: Producto): boolean {
    if (!this.sesionActiva) return false;
    return this.sesionActiva.lineasTicket.some(l => l.producto.id === producto.id);
  }

  // ─── Totales ─────────────────────────────────────────────────────────────────
  get subtotal(): number {
    if (!this.sesionActiva) return 0;
    return this.sesionActiva.lineasTicket.reduce((sum, l: any) => {
      if (l.esPorPeso) {
        return sum + l.producto.precio; // precio ya calculado (kg × €/kg)
      }
      return sum + l.producto.precio * l.cantidad;
    }, 0);
  }
  get iva(): number { return this.subtotal * (this.IVA_PORCENTAJE / 100); }
  get total(): number { return this.subtotal + this.iva; }

  // ─── Acciones ticket ─────────────────────────────────────────────────────────
  aparcar() {
    if (!this.sesionActiva) return;
    console.log('Ticket aparcado:', this.sesionActiva.lineasTicket);
  }

  anular() {
    if (!this.sesionActiva) return;
    this.sesionActiva.lineasTicket = [];
    this.sesionActiva.clienteActual = 'Cliente Directo';
    this._guardarSesiones();
  }

  // ─── Modal de Cobro ──────────────────────────────────────────────────────────
  isCobroOpen = false;
  metodoCobro: 'efectivo' | 'tarjeta' | 'mixto' = 'efectivo';

  // Efectivo
  entregadoEfectivo: number | null = null;
  billetesRapidos = [5, 10, 20, 50, 100];

  get cambioEfectivo(): number {
    return (this.entregadoEfectivo ?? 0) - this.total;
  }

  // Mixto
  mixtoTarjeta: number | null = null;
  mixtoEfectivo: number | null = null;
  mixtoRestante = 0;

  abrirModalCobro() {
    if (!this.sesionActiva || this.sesionActiva.lineasTicket.length === 0) return;
    this.metodoCobro = 'efectivo';
    this.entregadoEfectivo = null;
    this.mixtoTarjeta = null;
    this.mixtoEfectivo = null;
    this.mixtoRestante = this.total;
    this.isCobroOpen = true;
  }

  cerrarModalCobro() {
    this.isCobroOpen = false;
    this.cobroFase = 'seleccion';
  }

  seleccionarMetodo(m: 'efectivo' | 'tarjeta' | 'mixto') {
    this.metodoCobro = m;
    this.entregadoEfectivo = null;
    this.mixtoTarjeta = null;
    this.mixtoEfectivo = null;
    this.mixtoRestante = this.total;
  }

  usarBillete(valor: number) {
    this.entregadoEfectivo = +valor.toFixed(2);
  }

  recalcularMixto() {
    const t = +(this.mixtoTarjeta ?? 0);
    const e = +(this.mixtoEfectivo ?? 0);
    this.mixtoRestante = +(this.total - t - e).toFixed(2);
  }

  // ─── Modal Cliente ───────────────────────────────────────────────────────────
  isClienteModalOpen = false;
  clienteSearchQuery = '';
  clienteManualNombre = '';
  clienteManualCif = '';

  // Dummy data para búsqueda
  clientesGuardados = [
    { id: '#CLI-00123', nombre: 'Maria', apellidos: 'Gómez Garcia', contacto: 'maria.g@email.com' },
    { id: '#CLI-00124', nombre: 'Restaurante El Faro', apellidos: 'S.L.', contacto: '655 44 33 22' },
    { id: '#CLI-00125', nombre: 'Juan', apellidos: 'Pérez Rodríguez', contacto: 'juan.p@email.com' },
    { id: '#CLI-00126', nombre: 'Panadería San José', apellidos: 'López', contacto: '912 34 56 78' }
  ];

  get clientesFiltradosModal() {
    if (!this.clienteSearchQuery.trim()) return [];
    return this.clientesGuardados.filter(c =>
      `${c.nombre} ${c.apellidos}`.toLowerCase().includes(this.clienteSearchQuery.toLowerCase()) ||
      c.contacto.toLowerCase().includes(this.clienteSearchQuery.toLowerCase())
    );
  }

  abrirClienteModal() {
    this.isClienteModalOpen = true;
    this.clienteSearchQuery = '';
    this.clienteManualNombre = '';
    this.clienteManualCif = '';
  }

  cerrarClienteModal() {
    this.isClienteModalOpen = false;
  }

  seleccionarClienteModal(cliente: any) {
    if (this.sesionActiva) {
      this.sesionActiva.clienteActual = `${cliente.nombre} ${cliente.apellidos}`;
      this._guardarSesiones();
    }
    this.cerrarClienteModal();
  }

  asignarClienteManual() {
    if (this.sesionActiva && this.clienteManualNombre.trim()) {
      let nombreCif = this.clienteManualNombre.trim();
      if (this.clienteManualCif.trim()) {
        nombreCif += ` (CIF/DNI: ${this.clienteManualCif.trim()})`;
      }
      this.sesionActiva.clienteActual = nombreCif;
      this._guardarSesiones();
    }
    this.cerrarClienteModal();
  }

  // ─── Modal Crear Nuevo Cliente ───────────────────────────────────────────────
  isNuevoClienteModalOpen = false;
  nuevoCliente = {
    nombre: '',
    apellidos: '',
    contacto: '',
    tipo: 'Ocasional'
  };

  abrirNuevoClienteModal() {
    this.isNuevoClienteModalOpen = true;
    this.nuevoCliente = { nombre: '', apellidos: '', contacto: '', tipo: 'Directa' };
  }

  cerrarNuevoClienteModal() {
    this.isNuevoClienteModalOpen = false;
  }

  guardarNuevoCliente() {
    if (!this.nuevoCliente.nombre.trim()) return;

    // Generar un ID dummy
    const nuevoId = `#CLI-001${Math.floor(Math.random() * 90 + 30)}`;

    const clienteCreado = {
      id: nuevoId,
      nombre: this.nuevoCliente.nombre.trim(),
      apellidos: this.nuevoCliente.apellidos.trim(),
      contacto: this.nuevoCliente.contacto.trim(),
      tipo: this.nuevoCliente.tipo
    };

    // Añadir a la lista de búsqueda
    this.clientesGuardados.unshift(clienteCreado);

    // Asignar al ticket
    this.seleccionarClienteModal(clienteCreado);

    this.cerrarNuevoClienteModal();
  }


  cobroFase: 'seleccion' | 'exito' = 'seleccion';
  cobroExitosoFactura: any = null;
  cobroExitosoImporte: number = 0;
  cobroExitosoMetodoLabel: string = '';

  confirmarCobro() {
    if (!this.sesionActiva) return;

    const importe = this.total;
    let metodoPagoLabel = '';
    let metodoCobro = 'Efectivo';

    if (this.metodoCobro === 'efectivo') {
      metodoPagoLabel = `Efectivo (cambio: €${Math.max(0, this.cambioEfectivo).toFixed(2)})`;
    } else if (this.metodoCobro === 'tarjeta') {
      metodoPagoLabel = 'Tarjeta';
      metodoCobro = 'Tarjeta';
    } else {
      metodoPagoLabel = `Mixto — Tarjeta: €${(this.mixtoTarjeta ?? 0).toFixed(2)} · Efectivo: €${(this.mixtoEfectivo ?? 0).toFixed(2)}`;
      metodoCobro = 'Mixto';
    }

    // Crear la FacturaTPV
    const nuevaFactura = {
      id: 'FAC-' + Math.random().toString(36).substr(2, 6).toUpperCase(),
      fecha: new Date().toISOString(),
      empleado: this.sesionActiva.nombreEmpleado,
      cliente: this.sesionActiva.clienteActual,
      metodoCobro: metodoCobro,
      lineas: [...this.sesionActiva.lineasTicket],
      total: importe
    };

    // Registrar el cobro en el servicio
    this.myservice.registrarCobroTPV(this.sesionActiva.nombreEmpleado, importe, nuevaFactura);

    // Limpiar ticket y cliente
    this.sesionActiva.lineasTicket = [];
    this.sesionActiva.clienteActual = 'Cliente Directo';
    this._guardarSesiones();

    // Guardar los datos para la fase de éxito
    this.cobroExitosoImporte = importe;
    this.cobroExitosoMetodoLabel = metodoPagoLabel;
    this.cobroExitosoFactura = nuevaFactura;

    // Cambiar la fase a éxito sin cerrar el modal
    this.cobroFase = 'exito';
  }

  imprimirTicketExitoso() {
    this.imprimirTicket(this.cobroExitosoFactura);
    this.cerrarCobroExitoso();
  }

  cerrarCobroExitoso() {
    this.isCobroOpen = false;
    this.cobroFase = 'seleccion';
    this.cobroExitosoFactura = null;
    this.cobroExitosoImporte = 0;
    this.cobroExitosoMetodoLabel = '';
  }

  imprimirTicket(factura: any) {
    if (!factura || !factura.lineas) return;

    let lineasHtml = '';
    factura.lineas.forEach((l: any) => {
      const precio = Number(l.producto?.precio || 0);
      const cantidad = Number(l.cantidad || 0);
      lineasHtml += `
        <div style="display: flex; justify-content: space-between; font-size: 12px; margin-bottom: 2px;">
          <span>${cantidad}x ${l.producto?.nombre || 'Producto'}</span>
          <span>€${(precio * cantidad).toFixed(2)}</span>
        </div>`;
    });

    const total = Number(factura.total || 0).toFixed(2);

    const html = `<html><head><title>Ticket de Compra - ${factura.id}</title><style>
      body{font-family:'Courier New',Courier,monospace;display:flex;justify-content:center;margin:0;padding:0;}
      .ticket{width:80mm;padding:5mm;text-align:left;color:#000;}
      .center{text-align:center;}
      h2{font-size:18px;margin:0 0 5mm 0;}
      p{font-size:12px;margin:2px 0;}
      .sep{border-top:1px dashed #000;margin:5mm 0;}
      .total{font-size:16px;font-weight:bold;display:flex;justify-content:space-between;margin-top:5mm;}
      @media print{@page{size:80mm auto;margin:0;}}
    </style></head><body><div class="ticket">
      <div class="center">
        <h2>TICKET DE COMPRA</h2>
        <p><strong>Tienda Pueblo</strong></p>
        <p>ID: ${factura.id}</p>
        <p>Fecha: ${new Date(factura.fecha).toLocaleString()}</p>
      </div>
      <div class="sep"></div>
      <p><strong>Cliente:</strong> ${factura.cliente || 'Cliente Directo'}</p>
      <p><strong>Atendido por:</strong> ${factura.empleado}</p>
      <div class="sep"></div>
      ${lineasHtml}
      <div class="sep"></div>
      <div class="total">
        <span>TOTAL:</span>
        <span>€${total}</span>
      </div>
      <p style="margin-top:2mm; font-size:12px;">Método: ${factura.metodoCobro}</p>
      <div class="sep"></div>
      <p class="center" style="margin-top:5mm;">¡Gracias por su compra!</p>
    </div></body></html>`;

    const iframe = document.createElement('iframe');
    iframe.style.position = 'absolute';
    iframe.style.top = '-1000px';
    document.body.appendChild(iframe);

    const doc = iframe.contentWindow?.document;
    if (doc) {
      doc.open(); doc.write(html); doc.close();
      // Ejecutar de forma síncrona/segura
      setTimeout(() => {
        try {
          iframe.contentWindow?.focus();
          iframe.contentWindow?.print();
        } catch (e) {
          console.error('Error al imprimir:', e);
        } finally {
          setTimeout(() => {
            if (document.body.contains(iframe)) document.body.removeChild(iframe);
          }, 1000);
        }
      }, 100);
    }
  }

  // ─── Teclado numérico ────────────────────────────────────────────────────────

  cambiarModo(modo: 'codigo' | 'cantidad') {
    this.modoEntrada = modo;
    this.limpiarEntrada();
  }

  pulsarTecla(tecla: string) {
    this.errorEntrada = '';

    // Evitar múltiples comas
    if (tecla === ',' && this.valorEntrada.includes(',')) return;

    // Limitar longitud
    if (this.valorEntrada.length >= 12) return;

    this.valorEntrada += tecla;

    // En modo código, buscar producto en tiempo real
    if (this.modoEntrada === 'codigo') {
      this.buscarProductoPorCodigo(this.valorEntrada);
    }
  }

  borrarUltimo() {
    this.errorEntrada = '';
    this.valorEntrada = this.valorEntrada.slice(0, -1);

    if (this.modoEntrada === 'codigo') {
      if (this.valorEntrada) {
        this.buscarProductoPorCodigo(this.valorEntrada);
      } else {
        this.productoEncontrado = null;
        this.productoPendiente = null;
      }
    }
  }

  limpiarEntrada() {
    this.valorEntrada = '';
    this.errorEntrada = '';
    this.productoEncontrado = null;
    this.productoPendiente = null;
  }

  buscarProductoPorCodigo(codigo: string) {
    const encontrado = this.productos.find(
      p => p.codigo === codigo || p.id.toString() === codigo
    );
    if (encontrado) {
      this.productoEncontrado = encontrado;
      this.errorEntrada = '';
    } else {
      this.productoEncontrado = null;
    }
  }

  // ─── Lector QR / Código de Barras (Mock) ───────────────────────────────────
  async scanQR() {
    if (!this.sesionActiva) return;

    const alert = await this.alertController.create({
      header: 'Escáner QR / Código de Barras',
      message: 'Simulando lectura de cámara en dispositivo móvil o tablet...',
      buttons: [
        { text: 'Cancelar', role: 'cancel' },
        {
          text: 'Simular Lectura Exitosa',
          handler: () => {
            // Escoge un producto aleatorio del catálogo
            const productoAleatorio = this.productos[Math.floor(Math.random() * this.productos.length)];
            this.agregarAlTicket(productoAleatorio, 1);
          }
        }
      ]
    });
    await alert.present();
  }

  confirmarEntrada() {
    this.errorEntrada = '';

    // ─── Modo Código ─────────────────────────────────────────────────────────────
    if (this.modoEntrada === 'codigo') {
      if (!this.productoEncontrado) {
        this.errorEntrada = `Código "${this.valorEntrada}" no encontrado`;
        return;
      }
      if (this.productoEncontrado.agotado) {
        this.errorEntrada = 'Este producto está agotado';
        return;
      }
      // F&V → abrir modal de peso
      if (this.productoEncontrado.categoria === 'Frutas y Verduras') {
        this.abrirModalPeso(this.productoEncontrado);
        this.limpiarEntrada();
      } else {
        // Producto normal: pasar a modo cantidad
        this.productoPendiente = this.productoEncontrado;
        this.modoEntrada = 'cantidad';
        this.valorEntrada = '';
        this.productoEncontrado = null;
      }
      return;
    }

    // ─── Modo Cantidad ───────────────────────────────────────────────────────────
    const cantidad = parseFloat(this.valorEntrada.replace(',', '.'));

    if (!this.valorEntrada || isNaN(cantidad) || cantidad <= 0) {
      this.errorEntrada = 'Introduce una cantidad válida';
      return;
    }

    if (this.productoPendiente) {
      this.agregarAlTicket(this.productoPendiente, Math.round(cantidad));
      this.limpiarEntrada();
      this.modoEntrada = 'codigo';
    } else {
      if (!this.sesionActiva || this.sesionActiva.lineasTicket.length === 0) {
        this.errorEntrada = 'No hay productos en el ticket';
        return;
      }
      const ultima = this.sesionActiva.lineasTicket[this.sesionActiva.lineasTicket.length - 1];
      ultima.cantidad = Math.round(cantidad);
      this.limpiarEntrada();
    }
  }
}
