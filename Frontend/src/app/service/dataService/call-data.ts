import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CallData {
  // BehaviorSubject almacena el valor actual y emite cambios
  private dataSource = new BehaviorSubject<any[]>([]);
  // Observable para que los componentes se suscriban
  data$ = this.dataSource.asObservable();

  constructor() { }

  // Método para actualizar los datos desde cualquier ventana reemplazando el estado anterior
  setData(newData: any[]) {
    this.dataSource.next(newData);
  }

  // Método opcional para añadir un solo item (aunque usualmente es mejor recargar la lista completa)
  addItem(item: any) {
    const currentData = this.dataSource.value;
    this.dataSource.next([...currentData, item]);
  }

}
