import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SettingPagePageRoutingModule } from './setting-page-routing.module';

import { SettingPagePage } from './setting-page.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SettingPagePageRoutingModule
  ],
  declarations: [SettingPagePage]
})
export class SettingPagePageModule {}
