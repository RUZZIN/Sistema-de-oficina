import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClienteService } from '../../services/cliente.service';


@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent {


  ngOnInit(): void {
    this.clienteService.getClientes().subscribe((x) => {console.log(x)});
  }
  
  constructor(private router: Router, private clienteService: ClienteService) {}
}
