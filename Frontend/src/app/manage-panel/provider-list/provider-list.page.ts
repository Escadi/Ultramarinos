import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Myservice } from 'src/app/service/myservice';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-provider-list',
  templateUrl: './provider-list.page.html',
  styleUrls: ['./provider-list.page.scss'],
  standalone: false
})
export class ProviderListPage implements OnInit {
  proveedores: any[] = [];
  selectedCategory: any[] = [];
  
  // Filter & Pagination
  searchTerm: string = '';
  currentPage: number = 1;
  itemsPerPage: number = 8;
  filteredProveedores: any[] = [];

  // VARIABLES PARA GUARDAR LOS PROVEDORES
  Proveedor: any = {
    CifProveedor: '',
    nombre: '',
    direccion: '',
    telefono: '',
    email: '',
    idCategoria: 0
  };

  // VARIABLE PARA EDITAR 
  updateProveedor: string | null = null;

  // VARIABLES DE LOS MODAL
  isModalOpen: boolean = false;

  constructor(
    private router: Router,
    private myService: Myservice,
    private alertController: AlertController
  ) { }

  ngOnInit() {
    this.getAll();
  }

  // ─── DATA FETCHING ────────────────────────────────────────────────────────
  getAll() {
    this.myService.getProveedores().subscribe({
      next: (res: any) => {
        this.proveedores = res;
        this.filterProveedores();
      },
      error: (err: any) => console.log(err)
    });

    this.myService.getCategorias().subscribe({
      next: (res: any) => this.selectedCategory = res,
      error: (err: any) => console.log(err)
    });
  }

  // ─── FILTERING & PAGINATION ───────────────────────────────────────────────
  filterProveedores() {
    let filtered = this.proveedores;
    if (this.searchTerm.trim() !== '') {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(p =>
        p.nombre.toLowerCase().includes(term) ||
        p.CifProveedor.toLowerCase().includes(term) ||
        p.email.toLowerCase().includes(term) ||
        (p.categoria && p.categoria.nombreCategoria.toLowerCase().includes(term))
      );
    }
    this.filteredProveedores = filtered;
    this.currentPage = 1;
  }

  get paginatedProveedores() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredProveedores.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredProveedores.length / this.itemsPerPage) || 1;
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }

  prevPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  // ─── MODAL CONTROL ────────────────────────────────────────────────────────
  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
    this.ResetProveedor();
  }

  ResetProveedor() {
    this.Proveedor = {
      CifProveedor: '',
      nombre: '',
      direccion: '',
      telefono: '',
      email: '',
      idCategoria: 0
    };
    this.updateProveedor = null;
  }

  editProveedor(proveedor: any) {
    this.Proveedor = {
      CifProveedor: proveedor.CifProveedor,
      nombre: proveedor.nombre,
      direccion: proveedor.direccion,
      telefono: proveedor.telefono,
      email: proveedor.email,
      idCategoria: proveedor.idCategoria
    };
    this.updateProveedor = proveedor.CifProveedor;
    this.openModal();
  }

  // ─── CRUD OPERATIONS ──────────────────────────────────────────────────────
  async createProveedor() {
    if (!this.updateProveedor) {
      this.myService.postProveedores(this.Proveedor).subscribe({
        next: (res: any) => {
          this.getAll();
          this.closeModal();
        },
        error: (err: any) => console.log(err)
      });
    } else {
      this.myService.putProveedores(this.Proveedor.CifProveedor, this.Proveedor).subscribe({
        next: (res: any) => {
          this.getAll();
          this.closeModal();
        },
        error: (err: any) => console.log(err)
      });
    }
  }

  async deleteProveedor(id: string) {
    const alert = await this.alertController.create({
      header: 'Eliminar Proveedor',
      message: `¿Desea eliminar el proveedor con CIF: ${id}?`,
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel'
        },
        {
          text: 'Eliminar',
          handler: () => {
            this.myService.deleteProveedores(id).subscribe({
              next: () => this.getAll(),
              error: (err: any) => console.log(err)
            });
          }
        }
      ]
    });
    await alert.present();
  }

  goToAdminHome() {
    // Return to settings since we merged admin functionalities there
    this.router.navigateByUrl('/dashboard-control/setting-page');
  }

}
