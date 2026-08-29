import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'login-page',
    loadChildren: () => import('./login-page/login-page.module').then(m => m.LoginPagePageModule)
  },
  {
    path: '',
    redirectTo: 'login-page',
    pathMatch: 'full'
  },
  {
    path: 'dashboard-control',
    loadChildren: () => import('./tab-header-control/dashboard-control.module').then(m => m.DashboardControlPageModule)
  },
  {
    path: 'tpv',
    loadChildren: () => import('./tpv/tpv.module').then(m => m.TPVPageModule)
  },
  {
    path: 'caja',
    loadChildren: () => import('./caja/caja.module').then(m => m.CajaPageModule)
  },
  {
    path: 'facturas',
    loadChildren: () => import('./facturas/facturas.module').then( m => m.FacturasPageModule)
  },
  {
    path: 'cliente',
    loadChildren: () => import('./cliente/cliente.module').then( m => m.ClientePageModule)
  },




];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
