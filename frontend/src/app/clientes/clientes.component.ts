import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../services/cliente.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Cliente } from '../models/Cliente';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { DropdownModule } from 'primeng/dropdown';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [
    TableModule,
    DialogModule,
    ButtonModule,
    ToastModule,
    ToolbarModule,
    ConfirmDialogModule,
    InputTextModule,
    FormsModule,
    DropdownModule,
    CommonModule,
  ],
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css'],
  providers: [ConfirmationService, MessageService],
})
export class ClientesComponent implements OnInit {
  clientes: Cliente[] = [];
  clienteDialog: boolean = false;
  cliente: Cliente = {};
  selectedClientes: Cliente[] = [];
  submitted: boolean = false;

  tiposClientes = [
    { label: 'Pessoa Física', value: 'PessoaFisica' },
    { label: 'Pessoa Jurídica', value: 'PessoaJuridica' }
  ];

  constructor(
    private clienteService: ClienteService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.carregarClientes();
  }

  carregarClientes() {
    this.clienteService.buscarTodosClientes().subscribe((data) => {
      this.clientes = data;
    });
  }

  abrirNovo() {
    this.cliente = {};
    this.submitted = false;
    this.clienteDialog = true;
  }

  editarCliente(cliente: Cliente) {
    this.cliente = { ...cliente }; // Isso garante que o cliente a ser editado seja copiado corretamente para o form
    if (!this.cliente.cnpj) {
      this.cliente.cnpj = ''; // Garantir que o campo cnpj seja inicializado como string vazia se for null ou undefined
    }
    if (!this.cliente.inscricaoEstadual) {
      this.cliente.inscricaoEstadual = ''; // Mesmo para a inscrição estadual
    }
    if (!this.cliente.contato) {
      this.cliente.contato = ''; // E para o campo contato
    }
    this.clienteDialog = true; // Exibir o dialogo de edição
  }

  deletarCliente(cliente: Cliente) {
    this.confirmationService.confirm({
      message: `Você tem certeza que deseja deletar o cliente ${cliente.nome}?`,
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (cliente.id) {
          this.clienteService.deletarCliente(cliente.id).subscribe(() => {
            this.clientes = this.clientes.filter((c) => c.id !== cliente.id);
            this.messageService.add({
              severity: 'success',
              summary: 'Sucesso',
              detail: 'Cliente deletado',
              life: 3000,
            });
          });
        }
      },
    });
  }

  deletarClientesSelecionados() {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja deletar os clientes selecionados?',
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (this.selectedClientes && this.selectedClientes.length > 0) {
          const deleteRequests = this.selectedClientes
            .filter((cliente) => cliente.id !== undefined)
            .map((cliente) => this.clienteService.deletarCliente(cliente.id!));

          if (deleteRequests.length > 0) {
            forkJoin(deleteRequests).subscribe({
              next: () => {
                this.carregarClientes();
                this.selectedClientes = [];
                this.messageService.add({
                  severity: 'success',
                  summary: 'Sucesso',
                  detail: 'Clientes deletados com sucesso.',
                  life: 3000,
                });
              },
              error: () => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Erro',
                  detail: 'Falha ao deletar clientes.',
                  life: 3000,
                });
              },
            });
          } else {
            this.messageService.add({
              severity: 'warn',
              summary: 'Aviso',
              detail: 'Nenhum cliente válido para deletar.',
              life: 3000,
            });
          }
        }
      },
    });
  }

  salvarCliente() {
    this.submitted = true;

    if (this.cliente.nome?.trim()) {
      if (this.cliente.id) {
        // Atualizar cliente existente
        this.clienteService.atualizarCliente(this.cliente.id, this.cliente).subscribe(() => {
          this.carregarClientes();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Cliente atualizado',
            life: 3000,
          });
        });
      } else {
        // Criar novo cliente
        this.clienteService.salvarCliente(this.cliente).subscribe(() => {
          this.carregarClientes();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Cliente criado',
            life: 3000,
          });
        });
      }
      this.clienteDialog = false;
      this.cliente = {};
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Aviso',
        detail: 'O nome do cliente é obrigatório.',
        life: 3000,
      });
    }
  }

  esconderDialogo() {
    this.clienteDialog = false;
    this.submitted = false;
  }

  alterarTipoCliente() {
    if (!this.cliente.tipoCliente) return;

    if (this.cliente.tipoCliente === 'PessoaFisica') {
      this.cliente.cnpj = '';
      this.cliente.inscricaoEstadual = '';
    } else if (this.cliente.tipoCliente === 'PessoaJuridica') {
      this.cliente.cnpj = '';
    }
  }
}
