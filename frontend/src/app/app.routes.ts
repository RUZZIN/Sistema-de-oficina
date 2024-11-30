import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ClientesComponent } from './clientes/clientes.component';
import { FuncionariosComponent } from './funcionarios/funcionarios.component';
import { OsComponent } from './os/os.component';
import { PecasComponent } from './pecas/pecas.component';
import { ServicoComponent } from './servico/servico.component';
import { VeiculosComponent } from './veiculos/veiculos.component';
import { provideHttpClient } from '@angular/common/http';
import { EstoqueComponent } from './estoque/estoque.component';

export const routes: Routes = [
  { path: 'veiculos', component: VeiculosComponent },
  { path: 'clientes', component: ClientesComponent },
  { path: 'os', component: OsComponent },
  { path: 'servicos', component: ServicoComponent },
  { path: 'estoque', component: EstoqueComponent },
  { path: 'funcionarios', component: FuncionariosComponent },
];
