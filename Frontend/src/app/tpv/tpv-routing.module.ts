import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TPVPage } from './tpv.page';

const routes: Routes = [
  {
    path: '',
    component: TPVPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TPVPageRoutingModule {}
