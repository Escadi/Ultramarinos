import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ProviderListPageRoutingModule } from './provider-list-routing.module';

import { ProviderListPage } from './provider-list.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ProviderListPageRoutingModule
  ],
  declarations: [ProviderListPage]
})
export class ProviderListPageModule {}
