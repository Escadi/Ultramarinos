import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardControlPage } from './dashboard-control.page';

const routes: Routes = [
  {
    path: '',
    component: DashboardControlPage,
    children: [
      {
        path: 'dashboard-page',
        loadChildren: () => import('../dashboard-page/dashboard-page.module').then(m => m.DashboardPagePageModule)
      },
      {
        path: 'product-page',
        loadChildren: () => import('../product-page/product-page.module').then(m => m.ProductPagePageModule)
      },
      {
        path: 'delivery-page',
        loadChildren: () => import('../delivery-page/delivery-page.module').then(m => m.DeliveryPagePageModule)
      },
      {
        path: 'setting-page',
        loadChildren: () => import('../setting-page/setting-page.module').then(m => m.SettingPagePageModule)
      },
      {
        path: 'provider-page',
        loadChildren: () => import('../provider-page/provider-page.module').then(m => m.ProviderPagePageModule)
      },
      {
        path: 'facturas',
        loadChildren: () => import('../facturas/facturas.module').then(m => m.FacturasPageModule)
      },
      {
        path: 'product-list',
        loadChildren: () => import('../manage-panel/product-list/product-list.module').then(m => m.ProductListPageModule)
      },
      {
        path: 'provider-list',
        loadChildren: () => import('../manage-panel/provider-list/provider-list.module').then(m => m.ProviderListPageModule)
      },
      {
        path: 'worker-list',
        loadChildren: () => import('../admin-panel/worker-list/worker-list.module').then(m => m.WorkerListPageModule)
      },
      {
        path: 'tpv',
        loadChildren: () => import('../tpv/tpv.module').then(m => m.TPVPageModule)
      },
      {
        path: 'caja',
        loadChildren: () => import('../caja/caja.module').then(m => m.CajaPageModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('../cliente/cliente.module').then(m => m.ClientePageModule)
      },
      {
        path: '',
        redirectTo: 'dashboard-page',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashboardControlPageRoutingModule { }
