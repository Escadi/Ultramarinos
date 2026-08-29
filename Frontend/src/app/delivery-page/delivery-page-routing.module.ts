import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DeliveryPagePage } from './delivery-page.page';

const routes: Routes = [
  {
    path: '',
    component: DeliveryPagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DeliveryPagePageRoutingModule {}
