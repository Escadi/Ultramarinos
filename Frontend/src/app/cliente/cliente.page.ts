import { Component, OnInit } from '@angular/core';

interface Cliente {
  idString: string;
  nombre: string;
  apellidos: string;
  contacto: string;
  tipo: 'Fiel' | 'Profesional' | 'Ocasional';
  fechaCreacion: string;
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
      apellidos: 'Gómez Garcia',
      contacto: 'maria.g@email.com',
      tipo: 'Fiel',
      fechaCreacion: '12/01/2023'
    },
    {
      idString: '#CLI-00124',
      nombre: 'Restaurante El Faro',
      apellidos: 'S.L.',
      contacto: '655 44 33 22',
      tipo: 'Profesional',
      fechaCreacion: '15/02/2023'
    },
    {
      idString: '#CLI-00125',
      nombre: 'Juan',
      apellidos: 'Pérez Rodríguez',
      contacto: 'juan.p@email.com',
      tipo: 'Ocasional',
      fechaCreacion: '03/03/2023'
    },
    {
      idString: '#CLI-00126',
      nombre: 'Panadería San José',
      apellidos: 'López',
      contacto: '912 34 56 78',
      tipo: 'Profesional',
      fechaCreacion: '20/05/2023'
    }
  ];

  clientesFiltrados: Cliente[] = [];

  constructor() { }

  ngOnInit() {
    this.aplicarFiltros();
  }

  aplicarFiltros() {
    this.clientesFiltrados = this.clientes.filter(c => {
      const matchNombre = !this.filtros.nombre ||
        (c.nombre + ' ' + c.apellidos).toLowerCase().includes(this.filtros.nombre.toLowerCase());

      const matchContacto = !this.filtros.contacto ||
        c.contacto.toLowerCase().includes(this.filtros.contacto.toLowerCase());

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
}
