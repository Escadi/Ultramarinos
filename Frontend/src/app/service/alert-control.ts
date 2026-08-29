import { Injectable } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Injectable({
  providedIn: 'root',
})
export class AlertControl {

  constructor(
    private alertController: AlertController
  ) { }
  /**
   * ------------------------------------------------------------------------
   * ALERTA DE CONFIRMACION PARA ELIMINAR O ACTUALIZAR
   * ------------------------------------------------------------------------
   */
  async alertControl(header: string, message: string): Promise<boolean> {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header,
        message,
        buttons: [
          {
            text: 'Cancelar',
            role: 'cancel',
            handler: () => resolve(false)
          },
          {
            text: 'Aceptar',
            handler: () => resolve(true)
          }
        ]
      });
      await alert.present();
    });

  }
  /**
   * ------------------------------------------------------------------------
   * ALERTA DE INFORMACIÓN PARA MOSTRAR MENSAJES 
   * ------------------------------------------------------------------------
   */
  async alertInfo(header: string, message: string): Promise<void> {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header,
        message,
        buttons: [
          {
            text: 'Aceptar',
            handler: () => resolve()
          }
        ]
      });
      await alert.present();
    });
  }

}
