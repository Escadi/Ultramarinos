import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Camera, CameraResultType, CameraSource, GalleryPhoto, Photo } from '@capacitor/camera';
import { AlertController } from '@ionic/angular';
import { AlertControl } from 'src/app/service/alert-control';
import { Myservice } from 'src/app/service/myservice';
import { PhotoService } from 'src/app/service/photo-service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.page.html',
  styleUrls: ['./product-list.page.scss'],
  standalone: false
})
export class ProductListPage implements OnInit {

  //API URL
  apiUrl = environment.apiUrl;

  //VARIABLES PARA EL FILTROS
  selectedCategory: any[] = [];
  productos: any[] = [];
  centrosTrabajo: any[] = [];
  departamentos: any[] = [];
  filtroProductos: any[] = [];
  filtroSegmento: number = 0;

  //VARIABLES PARA EL FORMULARIO DE AGREGAR PRODUCTO

  idProducto: string = '';
  nombreProducto: string = '';
  descripcion: string = '';
  idCategoria: number = 0;
  idEmpleado: number = 1;
  precio: number = 0;
  filename: File | null = null;
  imagePreview: string | null = null;

  //VARIABLES PARA EL FORMULARIO DE AGREGAR CATEGORIA
  nombreCategoria: string = '';

  //VARIABLE PARA LA CAPTURA DE LA FOTO
  capturePhoto: any = null;

  //VARIABLES DEL MODAL
  isModalOpen: boolean = false;
  isEditModal: boolean = false;
  isAddProductModal: boolean = false;

  //PRODUCTO SELECCIONADO PARA ACTUALIZAR
  selectedProduct: any = null;

  //VARIABLE PARA EL TOOGLE
  isChangeToogle: boolean = false;

  constructor(
    private myservice: Myservice,
    private router: Router,
    private alertController: AlertControl,
    private photoService: PhotoService
  ) { }

  ngOnInit() {
    this.getAllData();
  }
  /***
  /***
   * FILTRAR PRODUCTOS POR BUSQUEDA (NOMBRE O CODIGO) MANTENIENDO EL FILTRO DE CATEGORIAS
   */
  filterProducts(event: any) {
    const filtro = event.target.value?.toLowerCase() || "";
    this.aplicarFiltros(filtro, this.filtroSegmento);
  }

  /***
   * FILTRAR PRODUCTOS AL CAMBIAR EL SEGMENTO DE CATEGORIAS
   */
  filterProductsBySegment(event: any) {
    this.filtroSegmento = event.detail.value;

    // Obtener el valor del buscador si es que la barra existe 
    // (podemos buscarlo directo del DOM o guardarlo en una variable, 
    // pero aquí asumimos string vacío si no se usa Two-Way Binding en el searchbar)
    const searchbar = document.querySelector('ion-searchbar') as HTMLIonSearchbarElement;
    const filtroTexto = searchbar && searchbar.value ? searchbar.value.toLowerCase() : "";

    this.aplicarFiltros(filtroTexto, this.filtroSegmento);
  }

  /***
   * FUNCIÓN CENTRAL DE FILTRADO (Aplica tanto la búsqueda de texto como la del segmento)
   */
  aplicarFiltros(filtroTexto: string, idCategoria: number) {
    this.filtroProductos = this.productos.filter((producto: any) => {
      // 1. Validar texto
      const nombre = producto.nombreProducto?.toLowerCase() || "";
      const codigo = producto.idProducto?.toString().toLowerCase() || "";
      const coincideTexto = !filtroTexto || nombre.includes(filtroTexto) || codigo.includes(filtroTexto);

      // 2. Validar categoría (Si es 0 o indefinido, mostramos todos)
      // Nota: Comparamos convirtiendo a String o Number para evitar problemas de tipos de la BD
      const coincideCategoria = !idCategoria || idCategoria == 0 || producto.idCategoria == idCategoria;

      return coincideTexto && coincideCategoria;
    });
  }

  /**
   * ----------------------------------------------------------------------------------------------------
   * TODAS LAS FUNCIONES DE MULTER EN LA PARTE BAJA PARA LA SUBIDA DE ARCHIVOS Y VER EN HTML
   * 
   * -------  --- --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --
   */

  /**
   * --------------------------------------------------------------------------------------------------------
   * FUNCION PARA SUBIR  EL ARCHIVO DE IMAGEN
   * --------------------------------------------------------------------------------------------------------
   */
  async uploadImage() {
    const data = await this.photoService.pickImage();
    this.capturePhoto = data.webPath;
    if (data.webPath) {
      this.imagePreview = data.webPath;
      const response = await fetch(data.webPath);
      const blob = await response.blob();
      this.filename = new File([blob], 'image.jpg', { type: blob.type || 'image/jpeg' });
    }
  }

  /**
   * --------------------------------------------------------------------------------------------------------
   * FUNCION PARA SUBIR  EL ARCHIVO DE IMAGEN POR CAMARA
   * --------------------------------------------------------------------------------------------------------
   */
  pickImage() {
    this.photoService.takePhoto().then(data => {
      this.capturePhoto = data.webPath ? data.webPath : "";
    });
  }

  /**
  * -------------------------------------------------------------------------------------------------------
  * FUNCION PARA REALIZAR LA FOTO CON LA CAMARA DEL MOVIL O POR WEB CAM
  * -------------------------------------------------------------------------------------------------------
  */
  discardImage() {
    this.capturePhoto = null;
    this.filename = null;
    this.imagePreview = null;
  }

  /**
  * -------------------------------------------------------------------------------------------------------
  * REALIZAMOS UNA FUNCION PARA COMPROBAR LA IMAGEN Y LANZARLA POR HTML
  * -------------------------------------------------------------------------------------------------------
  */
  getImage(filename: string) {
    if (!filename || filename === 'null' || filename === 'undefined') {
      return "https://ionicframework.com/docs/img/demos/avatar.svg";
    } else {
      // Retorns the image from the API bypass route
      return `${this.apiUrl}/api/images/${filename}`;
    }
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.filename = file;
      this.imagePreview = URL.createObjectURL(file);
    }
  }

  /**
   * --------------------------------------------------------------------------------------------------------
   * FUNCIONES PARA OBTENER LOS GET DE CADA UNO DE LAS FUNCIONALIDADES 
   * --------------------------------------------------------------------------------------------------------
   */

  getAllData() {
    this.myservice.getProductos().subscribe({ //OBTENER LOS PRODUCTOS
      next: (res: any) => {
        this.productos = res;
        this.filtroProductos = res;
      },
      error: (err: any) => {
        console.log(err);
      }
    });

    this.myservice.getCategorias().subscribe({ //OBTENER LAS CATEGORIAS
      next: (res: any) => {
        this.selectedCategory = res;
      },
      error: (err: any) => {
        console.log(err);
      }
    });

    this.myservice.getCentrosTrabajo().subscribe({ //OBTENER LOS CENTROS DE TRABAJO
      next: (res: any) => {
        this.centrosTrabajo = res;
      },
      error: (err: any) => {
        console.log(err);
      }
    });

    this.myservice.getDepartamentos().subscribe({ //OBTENER LOS DEPARTAMENTOS
      next: (res: any) => {
        this.departamentos = res;
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }
  /**
   * -------------------------------------------------------------------------------------------------------
   * FUNCIONES CRUD PARA PRODUCTOS , CREAR , MODIFICAR Y ELIMINAR PRODUCTO
   * -------------------------------------------------------------------------------------------------------
   */
  createProduct() { // CREAMOS EL PRODUCTO
    const formData = new FormData();
    formData.append('idProducto', this.idProducto);
    formData.append('nombreProducto', this.nombreProducto);
    formData.append('descripcion', this.descripcion);
    formData.append('idEmpleado', this.idEmpleado.toString());
    formData.append('idCategoria', this.idCategoria.toString());
    formData.append('precio', this.precio.toString());
    if (this.filename) {
      formData.append('file', this.filename);
    }

    this.myservice.postProductos(formData).subscribe({
      next: (res: any) => {
        console.log(res);
        this.getAllData();
        this.closeModal();
        this.resetForm();
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }

  async updateProduct() { // ACTUALIZAMOS EL PRODUCTO ENVIANDO LOS DATOS AL ION-MODAL
    if (!this.selectedProduct) return;
    const id = this.selectedProduct.idProducto;
    const formData = new FormData();
    formData.append('nombreProducto', this.nombreProducto);
    formData.append('descripcion', this.descripcion);
    formData.append('idEmpleado', this.idEmpleado.toString());
    formData.append('idCategoria', this.idCategoria.toString());
    formData.append('precio', this.precio.toString());
    if (this.filename) {
      formData.append('file', this.filename);
    }
    const confirmado = await this.alertController.alertControl('Editar Producto', '¿Quiereres Editar el producto');
    if (confirmado) {
      this.myservice.putProductos(id, formData).subscribe({
        next: (res: any) => {
          console.log(res);
          this.getAllData();
          this.closeAddProductModal();
          this.resetForm();
        },
        error: (err: any) => {
          console.log(err);
        }
      });
    }

  }

  async deleteProduct(id: number) {
    // ELIMINAMOS EL PRODUCTO CON EL BOTON QUE SE ENCUENTRA EN EL GRID
    const confirmado = await this.alertController.alertControl('Eliminar Producto', '¿Quiereres eliminar el producto');
    if (confirmado) {
      this.myservice.deleteProductos(id).subscribe({
        next: (res: any) => {
          console.log(res);
          this.getAllData();
        },
        error: (err: any) => {
          console.log(err);
        }
      });
    }
  }



  /**
   * --------------------------------------------------------------------------------------------------------
   * CRUD PARA CATEGORIAS ELIMINAR Y AGREGAR
   * --------------------------------------------------------------------------------------------------------
   */
  createCategory() {
    const category = {
      nombreCategoria: this.nombreCategoria
    }

    this.myservice.postCategorias(category).subscribe({
      next: (res: any) => {
        console.log(res);
        this.getAllData();
        this.closeModal();
        this.nombreCategoria = '';
      },
      error: (err: any) => {
        console.log(err);
      }
    });
  }

  async deleteCategory(id: number) {
    const confirmado = await this.alertController.alertControl('Eliminar Categoria', '¿Quiereres eliminar la categoria');
    if (confirmado) {
      this.myservice.deleteCategorias(id).subscribe({
        next: (res: any) => {
          console.log(res);
          this.getAllData();
        },
        error: (err: any) => {
          console.log(err);
        }
      });
    }
  }


  /**
   * --------------------------------------------------------------------------------------------------------
   * FUNCIONES DEL MODAL PARA LOS BOTONES DE AGREGAR Y EDITAR PRODUCTO
   * --------------------------------------------------------------------------------------------------------
   */
  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  openAddProductModal(producto: any) {
    this.selectedProduct = producto;
    this.idProducto = producto.idProducto;
    this.nombreProducto = producto.nombreProducto;
    this.descripcion = producto.descripcion;
    this.idCategoria = producto.idCategoria;
    this.idEmpleado = producto.idEmpleado;
    this.precio = producto.precio;
    this.isAddProductModal = true;
    if (producto.filename) {
      this.imagePreview = producto.filename;
    } else {
      this.imagePreview = null;
    }
  }

  closeAddProductModal() {
    this.isAddProductModal = false;
    this.selectedProduct = null;
    this.resetForm();
  }

  resetForm() {
    this.idProducto = '';
    this.nombreProducto = '';
    this.descripcion = '';
    this.idCategoria = 0;
    this.idEmpleado = 1;
    this.precio = 0;
    this.filename = null;
    this.imagePreview = null;
  }

  /**
   * --------------------------------------------------------------------------------------------------------
   * FUNCIONES PARA LA NAVEGACION
   * --------------------------------------------------------------------------------------------------------
   */
  goToAdminHome() {
    this.router.navigateByUrl('/dashboard-control/manage-home');
  }

}
