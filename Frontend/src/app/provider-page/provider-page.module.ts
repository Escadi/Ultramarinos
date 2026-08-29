import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ProviderPagePageRoutingModule } from './provider-page-routing.module';

import { ProviderPagePage } from './provider-page.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ProviderPagePageRoutingModule
  ],
  declarations: [ProviderPagePage]
})
export class ProviderPagePageModule {}
