import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';  // Para usar ngFor, ngIf, etc.
import { FormsModule } from '@angular/forms';   // Para ngModel (caso você queira usar no futuro)
import { ClienteService } from '../services/cliente.service';

@Component({
  selector: 'app-root',
  standalone: true,
  template:` <!-- Navbar -->
  <div class="navbar">
    <div class="navbar-brand">
      <a href="#">AutoGyn</a>
    </div>
  </div>
  
  <!-- Barra de Pesquisa -->
  <div class="search-bar">
    <input type="text" placeholder="Pesquisar opções..." (input)="onSearch($event)" />
  </div>
  
  <!-- Exibe as opções filtradas -->
  <div class="options-container">
    <div *ngFor="let option of filteredOptions" class="option" (click)="navigateTo(option)">
      {{ option }}
    </div>
  </div>
  
  <router-outlet></router-outlet>
  
  <!-- Rodapé -->
  <div class="footer">
    <p>&copy; 2024 AutoGyn - Sistema de Controle de Oficina Mecânica</p>
  </div>
  `,
  styleUrls: ['./app.component.css'],
  imports: [CommonModule, FormsModule, RouterModule]
})

export class AppComponent { // O chat que fez então não toque porque eu não sei arrumar essa merda
  options: string[] = [ 
    'Cadastro de Veículos',
    'Cadastro de Clientes',
    'Ordem de Serviços (OS)',
    'Cadastro de Serviços',
    'Cadastro de Peças',
    'Cadastro de Funcionários'
  ];

  filteredOptions: string[] = [...this.options];  //Aqui vai mostrar essa bosta de opcoes

  ngOnInit(): void {
    this.clienteService.getClientes().subscribe((x) => {console.log(x)});
  }

  // Função para redirecionar com base na opção
  navigateTo(option: string): void {
    const routeMap: { [key: string]: string } = {
      'Cadastro de Veículos': '/veiculos',
      'Cadastro de Clientes': '/clientes',
      'Ordem de Serviços (OS)': '/os',
      'Cadastro de Serviços': '/servicos',
      'Cadastro de Peças': '/pecas',
      'Cadastro de Funcionários': '/funcionarios',
    };

    const route = routeMap[option];
  if (route) {
    console.log(`Navigating to: ${route}`);
    this.router.navigate([route]);
  } else {
    console.error(`Route not found for option: ${option}`);
  }
  }

  constructor(private router: Router, private clienteService: ClienteService) {}

  // Função de pesquisa
  onSearch(event: Event) {
    const input = (event.target as HTMLInputElement).value;

    const normalizeString = (str: string) => str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");

    this.filteredOptions = this.options.filter(option =>
      normalizeString(option).toLowerCase().includes(normalizeString(input).toLowerCase())  // Não mexa aqui trem
    );
  }
}
