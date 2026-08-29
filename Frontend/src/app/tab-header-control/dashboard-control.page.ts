import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertControl } from '../service/alert-control';
import { Myservice } from '../service/myservice';
import { CallData } from '../service/dataService/call-data';

@Component({
  selector: 'app-dashboard-control',
  templateUrl: './dashboard-control.page.html',
  styleUrls: ['./dashboard-control.page.scss'],
  standalone: false
})
export class DashboardControlPage implements OnInit {
  detallesPedido: any[] = [];
  detalleCarrito: any[] = [];
  centroTrabajo: any[] = [];
  clientes: any[] = [];
  cantidadFinal: number = 0;

  //Variables para el modal del carrito
  isModalOpen = false;

  //VARIABLES PARA LOS PEDIDOS
  idCentro: number = 0;
  cifCliente: string = '';

  //VARIABLES PARA LOS DETALLES DEL PEDIDO
  idPedidoDetalle: number = 0;
  idDetalleProducto: number = 0;
  cantidad: number = 0;


  constructor(
    private router: Router,
    private myService: Myservice,
    private alert: AlertControl,
    private dataService: CallData
  ) { }

  ngOnInit() {
    this.getAllData();
    // SUSCRIBIRSE A LOS CAMBIOS DE DATOS GLOBALES PARA QUE SE ACTUALICE EL CARRITO
    this.dataService.data$.subscribe((newData: any[]) => {
      this.detalleCarrito = newData;
    });
  }
  /**
   * -------------------------------------------------------------------------------------------------------------
   * APERTURA Y CIERRE DEL MODAL DE CARRITO
   * -------------------------------------------------------------------------------------------------------------
   */
  openModalCart() {
    this.isModalOpen = true;
  }

  closeModalCart() {
    this.isModalOpen = false;
  }

  /***
   * ----------------------------------------------------------------------------------------------------
   * FUNCION DEL ION MODAL PARA EL CARRITO
   * ----------------------------------------------------------------------------------------------------
   */
  btnAdd(item: any) {
    item.cantidad++;
    this.cantidadFinal = this.cantidadFinal + 1;
  }

  btnRemove(item: any) {
    if (item.cantidad > 1) {
      item.cantidad--;
    }
    this.cantidadFinal = this.cantidadFinal - 1;
  }

  /**
   * ----------------------------------------------------------------------------------------------------
   * FUNCION GET DE DETALLES CARRITO PARA VER TODO EL CARRITO
   * ----------------------------------------------------------------------------------------------------
   */
  getAllData() {

    this.myService.getDetallesCarrito().subscribe({ // CARGAR DATOS DEL CARRITO
      next: (res: any) => {
        this.dataService.setData(res);
      },
      error: (err: any) => {
        console.log(err)
      }
    });

    this.myService.getCentrosTrabajo().subscribe({ // CARGAR DATOS DEL CENTRO DE TRABAJO
      next: (res: any) => {
        this.centroTrabajo = res;
      },
      error: (err: any) => {
        console.log(err)
      }
    });

    this.myService.getClientes().subscribe({ // CARGAR DATOS DEL CLIENTE
      next: (res: any) => {
        this.clientes = res;


      },
      error: (err: any) => console.log(err)
    });

  }


  /**
   * ----------------------------------------------------------------------------------------------------
   * FUNCIONES CRUD DE DETALLES CARRITO | PUT, POST, DELETE|
   * ----------------------------------------------------------------------------------------------------
   */

  deleteDetalleCarrito(id: number) {
    this.myService.deleteDetallesCarrito(id).subscribe({
      next: (res: any) => {
        this.getAllData();
      },
      error: (err: any) => {
        console.log(err)
      }
    });
  }

  /**
   * ----------------------------------------------------------------------------------------------------
   * FUNCIONES DETALLES PEDIDO Y PEDIDO | PUT, POST |
   * ----------------------------------------------------------------------------------------------------
   */
  createPedido() {
    console.log("Datos antes de crear pedido:", { cif: this.cifCliente, centro: this.idCentro });
    if (!this.cifCliente || !this.idCentro) {
      console.log("Faltan datos:", { cif: this.cifCliente, centro: this.idCentro });
      return;
    }

    const pedido = {
      cifCliente: this.cifCliente,
      idCentro: this.idCentro,
      idEmpleado: 1, // Valor por defecto hasta tener login
      fechaPedido: new Date(),
      estado: 'Pendiente'
    }

    console.log("Intentando crear pedido con datos:", pedido);

    this.myService.postPedidos(pedido).subscribe({
      next: (res: any) => {
        console.log("Respuesta del servidor al crear pedido:", res);
        const idPedido = res.idPedido || res.id || res.insertId;

        if (!idPedido) {
          console.error("No se pudo obtener el ID del pedido de la respuesta del servidor");
          return;
        }

        console.log("ID del pedido obtenido:", idPedido);

        // Recorremos todo el carrito para crear los detalles del pedido
        this.detalleCarrito.forEach(item => {
          const detail = {
            idPedido: idPedido,
            idDetalleProducto: item.idDetalleProducto,
            cantidad: item.cantidad
          };

          this.myService.postDetallePedidos(detail).subscribe({
            next: (resp: any) => {
              console.log("Detalle del pedido creado:", resp);
              // Borramos el item del carrito una vez procesado (opcional)
              this.deleteDetalleCarrito(item.idDetalleCarrito);
            },
            error: (err: any) => {
              console.log("Error al crear detalle:", err);
            }
          });
        });

        this.closeModalCart();
        this.cantidadFinal = 0;
        this.getAllData();
      },
      error: (err: any) => {
        console.log("Error al crear pedido principal:", err);
      }
    });
  }
  /**
   * ----------------------------------------------------------------------------------------------------
   * FUNCION LOGOUT
   * ----------------------------------------------------------------------------------------------------
   */
  getOut() {
    this.router.navigateByUrl('/login-page');
  }

}
