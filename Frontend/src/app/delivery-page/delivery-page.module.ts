import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DeliveryPagePageRoutingModule } from './delivery-page-routing.module';

import { DeliveryPagePage } from './delivery-page.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DeliveryPagePageRoutingModule
  ],
  declarations: [DeliveryPagePage]
})
export class DeliveryPagePageModule {}
