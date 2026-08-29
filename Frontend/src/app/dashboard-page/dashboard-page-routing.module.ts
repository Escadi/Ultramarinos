import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardPagePage } from './dashboard-page.page';

const routes: Routes = [
  {
    path: '',
    component: DashboardPagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardPagePageRoutingModule {}
