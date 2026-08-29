import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertControl } from 'src/app/service/alert-control';
import { Myservice } from 'src/app/service/myservice';

@Component({
  selector: 'app-worker-list',
  templateUrl: './worker-list.page.html',
  styleUrls: ['./worker-list.page.scss'],
  standalone: false
})
export class WorkerListPage implements OnInit {

  workers: any[] = [];
  centrosTrabajo: any[] = [];
  departamentos: any[] = [];
  cargos: any[] = [];

  // Filter & Pagination
  searchTerm: string = '';
  currentPage: number = 1;
  itemsPerPage: number = 8;
  filteredWorkers: any[] = [];

  // Modal Variables
  isModalOpen: boolean = false;
  isEditEmpleado: number | null = null;

  empleado = {
    idEmpleado: 0,
    nombre: '',
    apellido: '',
    telefono: '',
    direccion: '',
    email: '',
    idCentro: 0,
    idDepartamento: 0,
    idCargo: 0,
    rol: '',
    temporalPassword: ''
  };

  constructor(
    private router: Router,
    private myService: Myservice,
    private alertController: AlertControl
  ) { }

  ngOnInit() {
    this.getAll();
  }

  // ─── DATA FETCHING ────────────────────────────────────────────────────────
  getAll() {
    this.myService.getAllWorkers().subscribe({
      next: (res: any) => {
        this.workers = res;
        this.filterWorkers();
      },
      error: (err: any) => console.log(err)
    });

    this.myService.getCentrosTrabajo().subscribe({
      next: (res: any) => this.centrosTrabajo = res,
      error: (err: any) => console.log(err)
    });

    this.myService.getDepartamentos().subscribe({
      next: (res: any) => this.departamentos = res,
      error: (err: any) => console.log(err)
    });

    this.myService.getCargos().subscribe({
      next: (res: any) => this.cargos = res,
      error: (err: any) => console.log(err)
    });
  }

  // ─── FILTERING & PAGINATION ───────────────────────────────────────────────
  filterWorkers() {
    let filtered = this.workers;
    if (this.searchTerm.trim() !== '') {
      const term = this.searchTerm.toLowerCase();
      filtered = filtered.filter(w =>
        w.nombre.toLowerCase().includes(term) ||
        w.apellido.toLowerCase().includes(term) ||
        w.email.toLowerCase().includes(term) ||
        w.rol.toLowerCase().includes(term) ||
        w.idEmpleado.toString().includes(term)
      );
    }
    this.filteredWorkers = filtered;
    this.currentPage = 1;
  }

  get paginatedWorkers() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.filteredWorkers.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages(): number {
    return Math.ceil(this.filteredWorkers.length / this.itemsPerPage) || 1;
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
    this.resetEmpleado();
  }

  resetEmpleado() {
    this.empleado = {
      idEmpleado: 0,
      nombre: '',
      apellido: '',
      telefono: '',
      direccion: '',
      email: '',
      idCentro: 0,
      idDepartamento: 0,
      idCargo: 0,
      rol: '',
      temporalPassword: ''
    };
    this.isEditEmpleado = null;
  }

  editWorker(worker: any) {
    this.isEditEmpleado = worker.idEmpleado;
    this.empleado = {
      idEmpleado: worker.idEmpleado,
      nombre: worker.nombre,
      apellido: worker.apellido,
      telefono: worker.telefono,
      direccion: worker.direccion,
      email: worker.email,
      idCentro: worker.idCentro,
      idDepartamento: worker.idDepartamento,
      idCargo: worker.idCargo,
      rol: worker.rol,
      temporalPassword: worker.temporalPassword
    };
    this.openModal();
  }

  // ─── CRUD OPERATIONS ──────────────────────────────────────────────────────
  async createEmpleado() {
    if (!this.isEditEmpleado) {
      this.myService.postWorker(this.empleado).subscribe({
        next: (res: any) => {
          this.getAll();
          this.closeModal();
        },
        error: (err: any) => console.log(err)
      });
    } else {
      const confirmado = await this.alertController.alertControl('Editar Empleado', '¿Está seguro de que desea editar este empleado?');
      if (confirmado) {
        this.myService.putWorker(this.empleado.idEmpleado, this.empleado).subscribe({
          next: (res: any) => {
            this.getAll();
            this.closeModal();
          },
          error: (err: any) => console.log(err)
        });
      }
    }
  }

  async deleteEmpleado(id: number) {
    const confirmado = await this.alertController.alertControl('Eliminar Empleado', '¿Está seguro de que desea eliminar este empleado?');
    if (confirmado) {
      this.myService.deleteWorker(id).subscribe({
        next: (res: any) => {
          this.getAll();
        },
        error: (err: any) => console.log(err)
      });
    }
  }

  goToSettings() {
    this.router.navigateByUrl('/dashboard-control/setting-page');
  }

}
