import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-setting-page',
  templateUrl: './setting-page.page.html',
  styleUrls: ['./setting-page.page.scss'],
  standalone: false
})
export class SettingPagePage implements OnInit {

  // ─── MOCK DATOS DEL USUARIO ───────────────────────────────────────────────
  // Esto simula lo que vendría de un token JWT o Auth Service
  currentUser = {
    nombre: 'Carlos',
    apellido: 'López',
    email: 'admin@tiendapueblo.com',
    telefono: '600 123 456',
    dni: '12345678Z',
    rol: 'ADMIN', // Cambiar a 'USER' para ver la vista normal
    centroTrabajo: 'Centro Norte (Almacén Principal)',
    fechaAlta: '15/03/2023',
    avatar: 'CL'
  };

  // ─── MÓDULOS DE ADMINISTRACIÓN ───────────────────────────────────────────
  adminModules = [
    {
      title: 'Gestionar Trabajadores',
      description: 'Crear, modificar y eliminar empleados y sus permisos.',
      icon: 'people-outline',
      color: '#2563eb',
      route: '/dashboard-control/worker-list'
    },
    {
      title: 'Gestionar Proveedores',
      description: 'Crear, modificar y eliminar proveedores y sus permisos.',
      icon: 'people-outline',
      color: '#31c87f',
      route: '/dashboard-control/provider-list'
    },
    {
      title: 'Gestionar Productos',
      description: 'Crear, modificar y eliminar productos y sus permisos.',
      icon: 'people-outline',
      color: '#31c87f',
      route: '/dashboard-control/product-page'
    },
    {
      title: 'Gestionar compras a proveedores',
      description: 'Crear, modificar y eliminar compras a los proveedores y sus permisos.',
      icon: 'people-outline',
      color: '#31c87f',
      route: '/dashboard-control/provider-page'
    },
    {
      title: 'Listado de clientes',
      description: 'Ver, modificar y eliminar clientes.',
      icon: 'people-outline',
      color: '#f52f2fff',
      route: '/dashboard-control/cliente'
    }
  ];

  constructor(private router: Router) { }

  ngOnInit() { }

  /**
   * Navega a la ruta especificada
   */
  navigateTo(route: string) {
    this.router.navigateByUrl(route);
  }

  getOut() {
    this.router.navigateByUrl('/login-page');
  }

}
