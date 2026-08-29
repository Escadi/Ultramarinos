import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SettingPagePage } from './setting-page.page';

const routes: Routes = [
  {
    path: '',
    component: SettingPagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SettingPagePageRoutingModule {}
