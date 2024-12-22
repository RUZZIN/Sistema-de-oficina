import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
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
import { InputNumberModule } from 'primeng/inputnumber';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-funcionarios',
  standalone: true,
  template: `
    <h1 class="text-center text-2xl font-bold my-4">Funcionarios</h1>

    <p-toast></p-toast>
    <p-confirmDialog></p-confirmDialog>

    <div class="">
      <div class="grid">
        <div
          class="col-13"
          style="display: flex; justify-content: flex-end; margin: 10px;margin-right: 16px;"
        >
          <button
            pButton
            type="button"
            label="Adicionar"
            icon="pi pi-plus"
            (click)="openDialog()"
          ></button>
        </div>
      </div>

      <p-table
        [value]="funcionarios"
        [paginator]="true"
        [rows]="10"
        responsiveLayout="scroll"
      >
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
            <td>{{ funcionario.salario | currency : 'BRL' }}</td>
            <td>{{ funcionario.cargo }}</td>
            <td>{{ funcionario.telefone }}</td>
            <td>
              <button
                pButton
                icon="pi pi-pencil"
                severity="success"
                class="p-button-rounded p-button-success mr-2"
                (click)="editFuncionario(funcionario)"
              ></button>
              <button
                pButton
                 severity="danger"
                icon="pi pi-trash"
                class="p-button-rounded p-button-danger"
                (click)="deleteFuncionario(funcionario.id)"
              ></button>
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
      <form [formGroup]="funcionarioForm" (ngSubmit)="saveFuncionario()">
        <div class="p-fluid">
          <div class="field">
            <label for="nome">Nome</label>
            <input
              id="nome"
              type="text"
              pInputText
              formControlName="nome"
              required
            />
          </div>
          <div class="field">
            <label for="salario">Salário</label>
            <p-inputNumber
              id="salario"
              formControlName="salario"
              mode="currency"
              currency="BRL"
              locale="pt-BR"
            ></p-inputNumber>
          </div>
          <div class="field">
            <label for="cargo">Cargo</label>
            <input id="cargo" type="text" pInputText formControlName="cargo" />
          </div>
          <div class="field">
            <label for="telefone">Telefone</label>
            <input
              id="telefone"
              type="text"
              pInputText
              formControlName="telefone"
            />
          </div>
          <div class="field">
            <label for="dataNascimento">Data de Nascimento</label>
            <p-calendar
              id="dataNascimento"
              formControlName="dataNascimento"
              dateFormat="yy-mm-dd"
            ></p-calendar>
          </div>
          <div class="field">
            <label for="dataAdmissao">Data de Admissão</label>
            <p-calendar
              id="dataAdmissao"
              formControlName="dataAdmissao"
              dateFormat="yy-mm-dd"
            ></p-calendar>
          </div>
          <div class="field">
            <label for="dataDemissao">Data de Demissão</label>
            <p-calendar
              id="dataDemissao"
              formControlName="dataDemissao"
              dateFormat="yy-mm-dd"
            ></p-calendar>
          </div>
        </div>
        <p-footer>
          <button
            pButton
            type="button"
            label="Cancelar"
            icon="pi pi-times"
            (click)="closeDialog()"
            class="p-button-text"
          ></button>
          <button
            pButton
            type="submit"
            label="Salvar"
            icon="pi pi-check"
            [disabled]="funcionarioForm.invalid"
          ></button>
        </p-footer>
      </form>
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
    ConfirmDialogModule,
    InputNumberModule,
    ReactiveFormsModule,
  ],
  providers: [MessageService, ConfirmationService],
})
export class FuncionariosComponent implements OnInit {
  funcionarios: Funcionario[] = [];
  funcionarioForm: FormGroup;
  funcionarioDialog: boolean = false;
  editMode: boolean = false;
  selectedFuncionarioId?: number;

  constructor(
    private funcionariosService: FuncionariosService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private formBuilder: FormBuilder
  ) {
    this.funcionarioForm = this.formBuilder.group({
      nome: [''],
      salario: [0],
      dataNascimento: [''],
      dataAdmissao: [''],
      dataDemissao: [''],
      cargo: [''],
      endereco: [''],
      telefone: [''],
    });
  }

  ngOnInit(): void {
    this.listarFuncionarios();
  }

  listarFuncionarios(): void {
    this.funcionariosService.getFuncionarios().subscribe(
      (data) => (this.funcionarios = data),
      (error) => console.error('Erro ao carregar funcionários:', error)
    );
  }

  openDialog(): void {
    this.editMode = false;
    this.funcionarioForm.reset();
    this.funcionarioDialog = true;
  }

  saveFuncionario(): void {
    const funcionarioFormValue = this.funcionarioForm.value;

    const funcionario: Funcionario = {
      ...funcionarioFormValue,
    };

    if (this.editMode) {
      this.funcionariosService
        .updateFuncionario(this.selectedFuncionarioId!, funcionario)
        .subscribe({
          next: () => {
            this.listarFuncionarios();
            this.closeDialog();
            this.messageService.add({
              severity: 'success',
              summary: 'Sucesso',
              detail: 'Funcionário atualizado',
              life: 3000,
            });
          },
          error: (err) => console.error('Erro ao atualizar funcionário:', err),
        });
    } else {
      this.funcionariosService.addFuncionario(funcionario).subscribe({
        next: () => {
          this.listarFuncionarios();
          this.closeDialog();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Funcionário criado',
            life: 3000,
          });
        },
        error: (err) => console.error('Erro ao adicionar funcionário:', err),
      });
    }
  }

  editFuncionario(funcionario: Funcionario): void {
    this.editMode = true;
    this.selectedFuncionarioId = funcionario.id;
    this.funcionarioForm.patchValue(funcionario);
    this.funcionarioDialog = true;
  }

  deleteFuncionario(id: number): void {
    this.confirmationService.confirm({
      message: 'Tem certeza de que deseja excluir este funcionário?',
      header: 'Confirmação',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.funcionariosService.deleteFuncionario(id).subscribe(() => {
          this.listarFuncionarios();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Funcionário excluído',
            life: 3000,
          });
        });
      },
    });
  }

  closeDialog(): void {
    this.funcionarioDialog = false;
  }
}
