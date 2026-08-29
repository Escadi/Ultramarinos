import { Component, OnInit, OnDestroy } from '@angular/core';
import { AlertController } from '@ionic/angular';
import { Myservice, CajaEstado, TipoMovimientoCaja, MovimientoCaja, FacturaTPV } from '../service/myservice';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-caja',
  templateUrl: './caja.page.html',
  styleUrls: ['./caja.page.scss'],
  standalone: false
})
export class CajaPage implements OnInit, OnDestroy {

  // ─── Estado Global ───────────────────────────────────────────────────────────
  cajasActivas: CajaEstado[] = [];
  cajaActiva: CajaEstado | null = null;
  private sub: Subscription = new Subscription();
  fechaTurno: string = '';

  // Lista mock de empleados para elegir al abrir la caja
  empleadosMock: string[] = ['Admin Local', 'Juan Pérez', 'María Gómez', 'Carlos López'];

  // ─── Resumen del turno (de la caja activa) ───────────────────────────────────
  get efectivoVentas(): number { return this.cajaActiva ? (this.cajaActiva.efectivo - this.cajaActiva.fondoInicial) : 0; }
  get efectivoTotalEsperado(): number { return this.cajaActiva ? this.cajaActiva.efectivo : 0; }
  get tarjeta(): number { return this.cajaActiva ? this.cajaActiva.tarjeta : 0; }
  get fondoInicial(): number { return this.cajaActiva ? this.cajaActiva.fondoInicial : 0; }
  get horaApertura(): string { return this.cajaActiva ? this.cajaActiva.horaApertura : '--:--'; }
  get movimientos() { return this.cajaActiva ? this.cajaActiva.movimientos : []; }

  get totalEstimado(): number {
    return this.efectivoTotalEsperado + this.tarjeta;
  }

  // ─── Modal Historial y Facturas ──────────────────────────────────────────────

  isHistorialOpen: boolean = false;
  isFacturaOpen: boolean = false;
  facturaSeleccionada: FacturaTPV | null = null;
  filtroHistorial: 'Todos' | 'Entrada' | 'Salida' = 'Entrada'; // Por defecto muestra entradas según petición

  get movimientosFiltrados() {
    if (this.filtroHistorial === 'Todos') return this.movimientos;
    return this.movimientos.filter(m => m.tipo === this.filtroHistorial);
  }

  abrirHistorial() {
    this.isHistorialOpen = true;
  }

  cerrarHistorial() {
    this.isHistorialOpen = false;
  }

  abrirFactura(movimiento: MovimientoCaja) {
    if (!movimiento.facturaId || !this.cajaActiva) return;

    // Buscar la factura en el histórico de la caja
    const factura = this.cajaActiva.facturas.find(f => f.id === movimiento.facturaId);
    if (factura) {
      this.facturaSeleccionada = factura;
      this.isFacturaOpen = true;
    }
  }

  cerrarFactura() {
    this.isFacturaOpen = false;
    this.facturaSeleccionada = null;
  }

  // ─── Control de Modal Movimiento Manual ─────────────────────────────────────────────────────────
  tabRegistro: TipoMovimientoCaja = 'Entrada';
  importe: number = 0;
  concepto: string = 'Cambio de billetes';
  notas: string = '';

  conceptos: string[] = [
    'Cambio de billetes',
    'Pago a proveedor',
    'Gastos operativos',
    'Devolución cliente',
    'Fondo de caja',
    'Otros',
  ];

  // ─── Arqueo de Caja (Cierre) ─────────────────────────────────────────────────
  isArqueoOpen: boolean = false;
  arqueoTarjeta: number = 0;

  // Billetes
  b500: number = 0; b200: number = 0; b100: number = 0; b50: number = 0;
  b20: number = 0; b10: number = 0; b5: number = 0;
  // Monedas
  m2: number = 0; m1: number = 0; m50c: number = 0; m20c: number = 0;
  m10c: number = 0; m5c: number = 0; m2c: number = 0; m1c: number = 0;

  get arqueoEfectivo(): number {
    return ((this.b500 || 0) * 500) + ((this.b200 || 0) * 200) + ((this.b100 || 0) * 100) +
      ((this.b50 || 0) * 50) + ((this.b20 || 0) * 20) + ((this.b10 || 0) * 10) + ((this.b5 || 0) * 5) +
      ((this.m2 || 0) * 2) + ((this.m1 || 0) * 1) + ((this.m50c || 0) * 0.50) + ((this.m20c || 0) * 0.20) +
      ((this.m10c || 0) * 0.10) + ((this.m5c || 0) * 0.05) + ((this.m2c || 0) * 0.02) + ((this.m1c || 0) * 0.01);
  }

  constructor(
    public myservice: Myservice,
    private alertController: AlertController
  ) { }

  ngOnInit() {
    const ahora = new Date();
    this.fechaTurno = ahora.toLocaleDateString('es-ES', {
      weekday: 'long', day: 'numeric', month: 'long'
    });

    this.sub = this.myservice.cajasActivas$.subscribe(cajas => {
      this.cajasActivas = cajas;
      // Seleccionar la primera caja por defecto si no hay una activa válida
      if (this.cajasActivas.length > 0 && !this.cajaActiva) {
        this.cajaActiva = this.cajasActivas[0];
      } else if (this.cajaActiva) {
        // Actualizar la referencia a la caja seleccionada
        const actualizada = this.cajasActivas.find(c => c.empleado === this.cajaActiva?.empleado);
        this.cajaActiva = actualizada || null;
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  // Selector de Caja en la UI
  seleccionarCajaDe(empleado: string) {
    const caja = this.cajasActivas.find(c => c.empleado === empleado);
    if (caja) this.cajaActiva = caja;
  }

  // ─── Acciones de caja ────────────────────────────────────────────────────────

  // Mostrar modal simple para que elijan su nombre antes de abrir
  isAperturaOpen: boolean = false;
  empleadoSeleccionado: string = '';

  abrirCaja() {
    this.empleadoSeleccionado = '';
    this.isAperturaOpen = true;
  }

  cancelarApertura() {
    this.isAperturaOpen = false;
  }

  confirmarApertura() {
    if (!this.empleadoSeleccionado) return;
    this.myservice.abrirCaja(this.empleadoSeleccionado, 150.00); // 150 de fondo inicial hardcodeado por simplicidad
    this.isAperturaOpen = false;

    // Auto seleccionar la caja que acabamos de abrir
    this.seleccionarCajaDe(this.empleadoSeleccionado);
  }

  iniciarCierre() {
    if (!this.cajaActiva) return;
    // Reiniciar contadores para obligar a contar
    this.b500 = this.b200 = this.b100 = this.b50 = this.b20 = this.b10 = this.b5 = 0;
    this.m2 = this.m1 = this.m50c = this.m20c = this.m10c = this.m5c = this.m2c = this.m1c = 0;

    this.arqueoTarjeta = this.tarjeta;
    this.isArqueoOpen = true;
  }

  cancelarArqueo() {
    this.isArqueoOpen = false;
  }

  async confirmarArqueo() {
    if (!this.cajaActiva) return;
    const diferenciaEfectivo = this.arqueoEfectivo - this.efectivoTotalEsperado;
    const diferenciaTarjeta = this.arqueoTarjeta - this.cajaActiva.tarjeta;

    if (Math.abs(diferenciaEfectivo) > 0.005 || Math.abs(diferenciaTarjeta) > 0.005) {
      const alert = await this.alertController.create({
        header: 'Descuadre en Caja',
        message: 'No puedes cerrar la caja porque hay un descuadre pendiente de solucionar. Verifica el conteo de efectivo y tarjeta.',
        buttons: ['Entendido'],
        cssClass: 'alerta-descuadre'
      });
      await alert.present();
      return;
    }

    this.myservice.cerrarCaja(this.cajaActiva.empleado, diferenciaEfectivo, diferenciaTarjeta);

    this.isArqueoOpen = false;
  }

  get diferenciaEfectivo(): number {
    return this.arqueoEfectivo - this.efectivoTotalEsperado;
  }

  get diferenciaTarjeta(): number {
    return this.arqueoTarjeta - this.tarjeta;
  }

  get totalArqueo(): number {
    return this.arqueoEfectivo + this.arqueoTarjeta;
  }

  registrarMovimiento() {
    if (!this.importe || this.importe <= 0 || !this.cajaActiva) return;

    this.myservice.registrarMovimientoCaja(
      this.cajaActiva.empleado,
      this.tabRegistro,
      this.concepto + (this.notas ? ` — ${this.notas}` : ''),
      this.importe
    );

    // Reset form
    this.importe = 0;
    this.notas = '';
  }

  // ─── Helpers ─────────────────────────────────────────────────────────────────
  formatImporte(mov: any): string {
    const abs = Math.abs(mov.importe).toFixed(2);
    if (mov.tipo === 'Entrada') return `+ € ${abs}`;
    if (mov.tipo === 'Salida') return `- € ${abs}`;
    return `€ ${abs}`;
  }

  get importeValido(): boolean {
    return this.importe > 0;
  }
}
