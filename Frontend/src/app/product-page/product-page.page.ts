import { Component, OnInit } from '@angular/core';
import { Myservice } from '../service/myservice';
import { CallData } from '../service/dataService/call-data';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.page.html',
  styleUrls: ['./product-page.page.scss'],
  standalone: false
})
export class ProductPagePage implements OnInit {

  // ─── Datos crudos ─────────────────────────────────────────────────────────
  detalleProductos: any[] = [];
  categorias: any[] = [];
  detalleCarrito: any[] = [];

  // ─── Filtros ──────────────────────────────────────────────────────────────
  textoBusqueda: string = '';
  categoriaFiltro: number = 0;
  ordenActual: string = '';

  // ─── Dropdowns ────────────────────────────────────────────────────────────
  dropdownCatOpen: boolean = false;
  dropdownOrdOpen: boolean = false;

  // ─── Paginación ───────────────────────────────────────────────────────────
  paginaActual: number = 1;
  pageSize: number = 10;

  // ─── Modal ────────────────────────────────────────────────────────────────
  isModalOpen: boolean = false;
  modoEdicion: boolean = false;

  form: any = {
    idDetalleProducto: null,
    nombreProducto: '',
    idCategoria: 0,
    precioCompra: null,
    precioVenta: null,
    cantidad: null,
    descripcion: '',
  };

  // Compat: campos que el resto de la app puede usar
  filtroSegmento: number = 0;
  isChangeToogle: boolean = false;
  fitroDetalleProducto: any[] = [];
  filtroProductoCentro: any[] = [];
  idProducto: number = 0;
  nombreProducto: string = '';
  selectedCategory: any[] = [];

  constructor(
    private myservice: Myservice,
    private data: CallData
  ) {}

  ngOnInit() { this.getAllData(); }
  ionViewDidEnter() { this.getAllData(); }

  // ─── Carga de datos ───────────────────────────────────────────────────────
  getAllData() {
    this.myservice.getDetalleProductos().subscribe({
      next: (res: any) => {
        this.detalleProductos     = res;
        this.fitroDetalleProducto = res;   // compatibilidad
        this.aplicarFiltros();
      }
    });

    this.myservice.getCategorias().subscribe({
      next: (res: any) => {
        this.categorias       = res;
        this.selectedCategory = res;       // compatibilidad
      }
    });

    this.myservice.getDetallesCarrito().subscribe({
      next: (res: any) => {
        this.detalleCarrito = res;
        this.data.setData(res);
      }
    });
  }

  // ─── Filtrado y ordenación ────────────────────────────────────────────────
  get productosFiltrados(): any[] {
    let lista = [...this.detalleProductos];

    // Filtro texto
    if (this.textoBusqueda.trim()) {
      const q = this.textoBusqueda.toLowerCase();
      lista = lista.filter(p =>
        p.producto?.nombreProducto?.toLowerCase().includes(q) ||
        p.producto?.idProducto?.toString().includes(q)
      );
    }

    // Filtro categoría
    if (this.categoriaFiltro !== 0) {
      lista = lista.filter(p => p.producto?.idCategoria === this.categoriaFiltro);
    }

    // Ordenación
    switch (this.ordenActual) {
      case 'nombre-asc':  lista.sort((a, b) => a.producto?.nombreProducto?.localeCompare(b.producto?.nombreProducto)); break;
      case 'nombre-desc': lista.sort((a, b) => b.producto?.nombreProducto?.localeCompare(a.producto?.nombreProducto)); break;
      case 'precio-asc':  lista.sort((a, b) => (a.precioVenta ?? 0) - (b.precioVenta ?? 0)); break;
      case 'precio-desc': lista.sort((a, b) => (b.precioVenta ?? 0) - (a.precioVenta ?? 0)); break;
      case 'stock-asc':   lista.sort((a, b) => (a.cantidad ?? 0) - (b.cantidad ?? 0)); break;
      case 'stock-desc':  lista.sort((a, b) => (b.cantidad ?? 0) - (a.cantidad ?? 0)); break;
    }

    return lista;
  }

  get productosPagina(): any[] {
    const start = (this.paginaActual - 1) * this.pageSize;
    return this.productosFiltrados.slice(start, start + this.pageSize);
  }

  get totalPaginas(): number {
    return Math.max(1, Math.ceil(this.productosFiltrados.length / this.pageSize));
  }

  get paginasVisibles(): number[] {
    const total = this.totalPaginas;
    const current = this.paginaActual;
    const pages: number[] = [];
    const start = Math.max(1, current - 1);
    const end   = Math.min(total, start + 2);
    for (let i = start; i <= end; i++) pages.push(i);
    return pages;
  }

  aplicarFiltros() {
    this.paginaActual = 1;
    this.fitroDetalleProducto = this.productosFiltrados; // compatibilidad
    this.dropdownCatOpen = false;
    this.dropdownOrdOpen = false;
  }

  seleccionarCategoria(idCat: number) {
    this.categoriaFiltro = idCat;
    this.filtroSegmento = idCat;         // compatibilidad
    this.aplicarFiltros();
  }

  ordenar(clave: string) {
    this.ordenActual = clave;
    this.aplicarFiltros();
  }

  irPagina(n: number) {
    if (n >= 1 && n <= this.totalPaginas) this.paginaActual = n;
  }

  // ─── Compatibilidad con métodos anteriores ────────────────────────────────
  filterProducts(event: any) {
    this.textoBusqueda = event.target?.value || '';
    this.aplicarFiltros();
  }

  filterProductsBySegment(event: any) {
    this.seleccionarCategoria(event.detail?.value ?? 0);
  }

  // ─── Modal Nuevo / Editar ─────────────────────────────────────────────────
  abrirModalNuevo() {
    this.modoEdicion = false;
    this.form = { idDetalleProducto: null, nombreProducto: '', idCategoria: 0, precioCompra: null, precioVenta: null, cantidad: null, descripcion: '' };
    this.isModalOpen = true;
  }

  editarProducto(p: any) {
    this.modoEdicion = true;
    this.form = {
      idDetalleProducto: p.idDetalleProducto,
      nombreProducto: p.producto?.nombreProducto || '',
      idCategoria: p.producto?.idCategoria || 0,
      precioCompra: p.precioCompra,
      precioVenta:  p.precioVenta,
      cantidad:     p.cantidad,
      descripcion:  p.descripcion || '',
    };
    this.isModalOpen = true;
  }

  guardarProducto() {
    if (!this.form.nombreProducto || !this.form.precioVenta) return;

    const cat = this.categorias.find(c => c.idCategoria === +this.form.idCategoria);
    const payload = {
      idDetalleProducto: this.form.idDetalleProducto || Date.now(),
      producto: {
        idProducto:     this.form.idDetalleProducto || Date.now(),
        nombreProducto: this.form.nombreProducto,
        descripcion:    this.form.descripcion,
        idCategoria:    +this.form.idCategoria,
      },
      categoria:    cat || { idCategoria: 0, nombreCategoria: 'Sin categoría' },
      precioCompra: +this.form.precioCompra || 0,
      precioVenta:  +this.form.precioVenta  || 0,
      cantidad:     +this.form.cantidad     || 0,
      descripcion:  this.form.descripcion,
      imagen:       '',
    };

    if (this.modoEdicion) {
      const idx = this.detalleProductos.findIndex(p => p.idDetalleProducto === payload.idDetalleProducto);
      if (idx !== -1) this.detalleProductos[idx] = payload;
    } else {
      this.detalleProductos.unshift(payload);
    }

    this.aplicarFiltros();
    this.cerrarModal();
  }

  eliminarProducto(p: any) {
    if (!confirm(`¿Eliminar "${p.producto?.nombreProducto}"?`)) return;
    this.detalleProductos = this.detalleProductos.filter(x => x.idDetalleProducto !== p.idDetalleProducto);
    this.aplicarFiltros();
  }

  cerrarModal() {
    this.isModalOpen = false;
  }

  // Compatibilidad antigua
  openModal()       { this.isModalOpen = true; }
  closeModal()      { this.isModalOpen = false; }
  openModalCentro(producto: any) { this.editarProducto(producto); }
  addCarrito(producto: any) {
    const dc = { idDetalleProducto: producto.idDetalleProducto, cantidad: 0 };
    this.myservice.postDetallesCarrito(dc).subscribe({ next: () => this.getAllData() });
  }

  // ─── Helpers visual ───────────────────────────────────────────────────────
  getColorCategoria(nombre: string): string {
    const mapa: Record<string, string> = {
      'Bebidas':   '#2563eb',
      'Frescos':   '#16a34a',
      'Panadería': '#d97706',
      'Lácteos':   '#7c3aed',
      'Limpieza':  '#0891b2',
    };
    return mapa[nombre] ?? '#64748b';
  }

  getIconoCategoria(nombre: string): string {
    const mapa: Record<string, string> = {
      'Bebidas':   'cafe-outline',
      'Frescos':   'leaf-outline',
      'Panadería': 'pizza-outline',
      'Lácteos':   'nutrition-outline',
      'Limpieza':  'sparkles-outline',
    };
    return mapa[nombre] ?? 'cube-outline';
  }
}
