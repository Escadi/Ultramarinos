import { Component, OnInit } from '@angular/core';

export interface Cliente {
  idString: string;
  nombre: string;
  apellidos: string;
  contacto: string;
  telefono?: string;
  email?: string;
  tipo: 'Fiel' | 'Profesional' | 'Ocasional';
  fechaCreacion: string;
  direccion?: string;
  cifNif?: string;
  totalCompras?: number;
  notas?: string;
}

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.page.html',
  styleUrls: ['./cliente.page.scss'],
  standalone: false
})
export class ClientePage implements OnInit {

  filtros = {
    nombre: '',
    contacto: '',
    tipo: ''
  };

  clientes: Cliente[] = [
    {
      idString: '#CLI-00123',
      nombre: 'Maria',
      apellidos: 'Gómez García',
      contacto: 'maria.g@email.com',
      telefono: '612 345 678',
      email: 'maria.g@email.com',
      tipo: 'Fiel',
      fechaCreacion: '12/01/2023',
      direccion: 'Calle Mayor 14, 2º B, Madrid',
      cifNif: '45678912A',
      totalCompras: 1450.80,
      notas: 'Prefiere entregas por la mañana. Clienta habitual de productos ecológicos.'
    },
    {
      idString: '#CLI-00124',
      nombre: 'Restaurante El Faro',
      apellidos: 'S.L.',
      contacto: '655 44 33 22',
      telefono: '655 44 33 22',
      email: 'pedidos@restauranteelfaro.es',
      tipo: 'Profesional',
      fechaCreacion: '15/02/2023',
      direccion: 'Paseo Marítimo 88, Valencia',
      cifNif: 'B-98765432',
      totalCompras: 8320.50,
      notas: 'Facturación mensual consolidada. Pedidos de gran volumen.'
    },
    {
      idString: '#CLI-00125',
      nombre: 'Juan',
      apellidos: 'Pérez Rodríguez',
      contacto: 'juan.p@email.com',
      telefono: '677 88 99 00',
      email: 'juan.p@email.com',
      tipo: 'Ocasional',
      fechaCreacion: '03/03/2023',
      direccion: 'Avda. de la Constitución 5, Sevilla',
      cifNif: '12345678Z',
      totalCompras: 85.20,
      notas: 'Compras esporádicas en tienda física.'
    },
    {
      idString: '#CLI-00126',
      nombre: 'Panadería San José',
      apellidos: 'López',
      contacto: '912 34 56 78',
      telefono: '912 34 56 78',
      email: 'contacto@panaderiasanjose.com',
      tipo: 'Profesional',
      fechaCreacion: '20/05/2023',
      direccion: 'Plaza Mayor 3, Toledo',
      cifNif: 'B-11223344',
      totalCompras: 3410.00,
      notas: 'Proveedor y cliente cruzado de harinas especiales.'
    }
  ];

  clientesFiltrados: Cliente[] = [];

  // ─── Modales ────────────────────────────────────────────────────────────────
  isDetalleOpen: boolean = false;
  isEditarOpen: boolean = false;
  clienteSeleccionado: Cliente | null = null;

  clienteForm: Cliente = {
    idString: '',
    nombre: '',
    apellidos: '',
    contacto: '',
    telefono: '',
    email: '',
    tipo: 'Ocasional',
    fechaCreacion: '',
    direccion: '',
    cifNif: '',
    notas: ''
  };

  constructor() { }

  ngOnInit() {
    this.aplicarFiltros();
  }

  aplicarFiltros() {
    this.clientesFiltrados = this.clientes.filter(c => {
      const matchNombre = !this.filtros.nombre ||
        (c.nombre + ' ' + c.apellidos).toLowerCase().includes(this.filtros.nombre.toLowerCase());

      const matchContacto = !this.filtros.contacto ||
        c.contacto.toLowerCase().includes(this.filtros.contacto.toLowerCase()) ||
        (c.telefono && c.telefono.toLowerCase().includes(this.filtros.contacto.toLowerCase())) ||
        (c.email && c.email.toLowerCase().includes(this.filtros.contacto.toLowerCase()));

      const matchTipo = !this.filtros.tipo || c.tipo.toLowerCase() === this.filtros.tipo.toLowerCase();

      return matchNombre && matchContacto && matchTipo;
    });
  }

  limpiarFiltros() {
    this.filtros = {
      nombre: '',
      contacto: '',
      tipo: ''
    };
    this.aplicarFiltros();
  }

  // ─── Métodos para Modal Detalle ─────────────────────────────────────────────
  verDetalle(cliente: Cliente) {
    this.clienteSeleccionado = { ...cliente };
    this.isDetalleOpen = true;
  }

  cerrarDetalle() {
    this.isDetalleOpen = false;
    this.clienteSeleccionado = null;
  }

  // ─── Métodos para Modal Edición ─────────────────────────────────────────────
  abrirEditar(cliente: Cliente) {
    this.clienteForm = {
      ...cliente,
      telefono: cliente.telefono || (cliente.contacto.includes('@') ? '' : cliente.contacto),
      email: cliente.email || (cliente.contacto.includes('@') ? cliente.contacto : ''),
    };
    this.isEditarOpen = true;
  }

  abrirEditarDesdeDetalle() {
    if (this.clienteSeleccionado) {
      this.abrirEditar(this.clienteSeleccionado);
      this.isDetalleOpen = false;
    }
  }

  cerrarEditar() {
    this.isEditarOpen = false;
  }

  guardarCliente() {
    if (!this.clienteForm.nombre.trim()) return;

    // Actualiza contacto si aplica
    this.clienteForm.contacto = this.clienteForm.telefono || this.clienteForm.email || this.clienteForm.contacto;

    const idx = this.clientes.findIndex(c => c.idString === this.clienteForm.idString);
    if (idx !== -1) {
      this.clientes[idx] = { ...this.clienteForm };
    }
    this.aplicarFiltros();
    this.isEditarOpen = false;
  }
}
