import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProviderPagePage } from './provider-page.page';

const routes: Routes = [
  {
    path: '',
    component: ProviderPagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProviderPagePageRoutingModule {}
