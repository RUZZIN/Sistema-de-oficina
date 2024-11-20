import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Importação do FormsModule
import { CommonModule } from '@angular/common'; // Importação do CommonModule para ngIf e ngFor

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  templateUrl: './funcionarios.component.html',
  styleUrls: ['./funcionarios.component.css'],
  imports: [FormsModule, CommonModule], // Adicione o FormsModule aqui
})
export class FuncionariosComponent {
  novoFuncionario: string = '';
  funcionarios: string[] = [];

  salvarFuncionario() {
    if (this.novoFuncionario.trim()) {
      this.funcionarios.push(this.novoFuncionario.trim());
      this.novoFuncionario = '';
    }
  }

  excluirFuncionario() {
    if (this.funcionarios.length > 0) {
      this.funcionarios.pop();
    }
  }

  voltarParaInicial() {
    console.log('Voltando para a tela inicial...');
  }
}
