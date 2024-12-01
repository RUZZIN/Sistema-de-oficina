import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { CalendarModule } from 'primeng/calendar';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { FuncionariosService } from '../../services/funcionario.service';
import { Funcionario } from '../models/Funcionario';
import { ToolbarModule } from 'primeng/toolbar';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  template: `
    <p-toast></p-toast>
    <p-confirmDialog></p-confirmDialog>

    <div class="card">
      <p-toolbar>
        <div class="p-toolbar-group-left">
          <button pButton type="button" label="Novo Funcionário" icon="pi pi-plus" class="p-button-success" (click)="openNew()"></button>
        </div>
      </p-toolbar>

      <p-table [value]="funcionarios" [paginator]="true" [rows]="10" responsiveLayout="scroll">
        <ng-template pTemplate="header">
          <tr>
            <th>Nome</th>
            <th>Salário</th>
            <th>Cargo</th>
            <th>Telefone</th>
            <th>Ações</th>
          </tr>
        </ng-template>
        <ng-template pTemplate="body" let-funcionario>
          <tr>
            <td>{{ funcionario.nome }}</td>
            <td>{{ funcionario.salario | currency: 'BRL' }}</td>
            <td>{{ funcionario.cargo }}</td>
            <td>{{ funcionario.telefone }}</td>
            <td>
              <button pButton icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" (click)="editFuncionario(funcionario)"></button>
              <button pButton icon="pi pi-trash" class="p-button-rounded p-button-danger" (click)="deleteFuncionario(funcionario)"></button>
            </td>
          </tr>
        </ng-template>
      </p-table>
    </div>

    <p-dialog
  [(visible)]="funcionarioDialog"
  [modal]="true"
  [style]="{ width: '450px' }"
  header="Detalhes do Funcionário"
  [closable]="false"
>
  <div class="p-fluid">
    <div class="field">
      <label for="nome">Nome</label>
      <input id="nome" type="text" pInputText [(ngModel)]="funcionario.nome" required />
    </div>
    <div class="field">
      <label for="salario">Salário</label>
      <input id="salario" type="number" pInputText [(ngModel)]="funcionario.salario" required />
    </div>
    <div class="field">
      <label for="cargo">Cargo</label>
      <input id="cargo" type="text" pInputText [(ngModel)]="funcionario.cargo" />
    </div>
    <div class="field">
      <label for="telefone">Telefone</label>
      <input id="telefone" type="text" pInputText [(ngModel)]="funcionario.telefone" />
    </div>
    <div class="field">
      <label for="dataNascimento">Data de Nascimento</label>
      <p-calendar id="dataNascimento" [(ngModel)]="funcionario.dataNascimento" dateFormat="yy-mm-dd"></p-calendar>
    </div>
    <div class="field">
      <label for="dataAdmissao">Data de Admissão</label>
      <p-calendar id="dataAdmissao" [(ngModel)]="funcionario.dataAdmissao" dateFormat="yy-mm-dd"></p-calendar>
    </div>
    <div class="field">
      <label for="dataDemissao">Data de Demissão</label>
      <p-calendar id="dataDemissao" [(ngModel)]="funcionario.dataDemissao" dateFormat="yy-mm-dd"></p-calendar>
    </div>
  </div>
  <p-footer>
    <button pButton type="button" label="Cancelar" icon="pi pi-times" (click)="hideDialog()" class="p-button-text"></button>
    <button pButton type="button" label="Salvar" icon="pi pi-check" (click)="saveFuncionario()" [disabled]="!funcionario.nome"></button>
  </p-footer>
</p-dialog>


    
  `,
  styleUrls: ['./funcionarios.component.css'],
  imports: [
    ToolbarModule,
    FormsModule,
    CommonModule,
    TableModule,
    ButtonModule,
    DialogModule,
    InputTextModule,
    CalendarModule,
    ToastModule,
    ConfirmDialogModule
  ],
  providers: [MessageService, ConfirmationService]
})
export class FuncionariosComponent implements OnInit {
  funcionarios: Funcionario[] = [];
  funcionarioDialog: boolean = false;
  funcionario: Funcionario = {
    id: 0,
    nome: '',
    salario: 0,
    dataNascimento: '',
    dataAdmissao: '',
    dataDemissao: '',
    cargo: '',
    endereco: '',
    telefone: '',
    email: '',
    cpf: '',
    rg: '',
    situacao: ''
  };  
  submitted: boolean = false;

  constructor(
    private funcionariosService: FuncionariosService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.listarFuncionarios();
  }

  listarFuncionarios(): void {
    this.funcionariosService.getFuncionarios().subscribe(
      (data) => (this.funcionarios = data),
      (error) => console.error(error)
    );
  }

  openNew(): void {
      this.funcionario = {
          id: 0,
          nome: '',
          salario: 0,
          dataNascimento: '',
          dataAdmissao: '',
          dataDemissao: '',
          cargo: '',
          endereco: '',
          telefone: '',
          email: '',
          cpf: '',
          rg: '',
          situacao: ''
      };
      this.submitted = false;
      this.funcionarioDialog = true;
  }

  editFuncionario(funcionario: Funcionario): void {
    this.funcionario = { ...funcionario };
    this.funcionarioDialog = true;
  }

  deleteFuncionario(funcionario: Funcionario): void {
    this.confirmationService.confirm({
      message: `Tem certeza de que deseja excluir ${funcionario.nome}?`,
      header: 'Confirmação',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (funcionario.id) {
          this.funcionariosService.deleteFuncionario(funcionario.id).subscribe(() => {
            this.listarFuncionarios();
            this.messageService.add({
              severity: 'success',
              summary: 'Sucesso',
              detail: 'Funcionário excluído',
              life: 3000
            });
          });
        }
      }
    });
  }

  saveFuncionario(): void {
    this.submitted = true;
  
    // Se o nome e o salário estão presentes, podemos continuar
    if (this.funcionario.nome && this.funcionario.salario) {
      // Garantir que as datas estejam no formato correto
      this.funcionario.dataNascimento = this.funcionario.dataNascimento || "";
      this.funcionario.dataAdmissao = this.funcionario.dataAdmissao || "";
      this.funcionario.dataDemissao = this.funcionario.dataDemissao || "";
  
      if (this.funcionario.id) {
        // Atualizar o funcionário no backend
        this.funcionariosService.updateFuncionario(this.funcionario.id, this.funcionario).subscribe(() => {
          this.listarFuncionarios();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Funcionário atualizado',
            life: 3000
          });
        });
      } else {
        // Adicionar novo funcionário
        this.funcionariosService.addFuncionario(this.funcionario).subscribe(() => {
          this.listarFuncionarios();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Funcionário criado',
            life: 3000
          });
        });
      }
  
      // Fechar o diálogo após a operação
      this.funcionarioDialog = false;
      // Resetar os campos do funcionário após salvar
      this.funcionario = {
        id: 0,
        nome: '',
        salario: 0,
        dataNascimento: "",  // Alterado para null
        dataAdmissao: "",    // Alterado para null
        dataDemissao: "",    // Alterado para null
        cargo: '',
        endereco: '',
        telefone: '',
        email: '',
        cpf: '',
        rg: '',
        situacao: ''
      };
    }
  }
  

  hideDialog(): void {
    this.funcionarioDialog = false;
    this.submitted = false;
  }
}
