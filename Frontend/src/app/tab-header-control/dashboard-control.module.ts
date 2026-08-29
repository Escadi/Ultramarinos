import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DashboardControlPageRoutingModule } from './dashboard-control-routing.module';

import { DashboardControlPage } from './dashboard-control.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DashboardControlPageRoutingModule
  ],
  declarations: [DashboardControlPage]
})
export class DashboardControlPageModule {}
