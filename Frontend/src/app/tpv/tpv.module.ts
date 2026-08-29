import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TPVPageRoutingModule } from './tpv-routing.module';

import { TPVPage } from './tpv.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TPVPageRoutingModule
  ],
  declarations: [TPVPage]
})
export class TPVPageModule {}
