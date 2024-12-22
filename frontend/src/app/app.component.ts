import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common'; // Para usar ngFor, ngIf, etc.
import { FormsModule } from '@angular/forms'; // Para ngModel (caso você queira usar no futuro)

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [CommonModule, FormsModule, RouterModule]
})
export class AppComponent {
  menuOpen = false;

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  logout() {
    console.log('Logout acionado');
    // Adicione aqui a lógica para realizar logout, por exemplo:
    // Redirecionar para a página de login
    // Limpar tokens ou sessão
  }

  constructor(private router: Router) {}
}
