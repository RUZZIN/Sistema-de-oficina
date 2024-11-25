import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';  // Para usar ngFor, ngIf, etc.
import { FormsModule } from '@angular/forms';   // Para ngModel (caso você queira usar no futuro)
import { ClienteService } from '../services/cliente.service';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [CommonModule, FormsModule, RouterModule]
})

export class AppComponent { 


  constructor(private router: Router, private clienteService: ClienteService) { }
}
