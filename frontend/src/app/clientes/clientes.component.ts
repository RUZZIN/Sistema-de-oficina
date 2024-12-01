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
  cliente: Cliente = { nome: '', tipoCliente: '' };
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
    console.log("Carregando clientes...");
    this.clienteService.getClientes().subscribe((data) => {
      this.clientes = data;
    });
  }

  abrirNovo() {
    this.cliente = { nome: '', tipoCliente: '' };
    this.submitted = false;
    this.clienteDialog = true;
  }

  editarCliente(cliente: Cliente) {
    this.cliente = { ...cliente };
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
            //espera 1 segundo e recarrega a página
            setTimeout(() => {
              this.carregarClientes();
              this.carregarClientes();
              this.carregarClientes();
              this.carregarClientes();
              this.carregarClientes();
            }, 1500);
            
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
            this.carregarClientes();
            this.carregarClientes();
            this.carregarClientes();
            this.carregarClientes();
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
    this.carregarClientes();
    this.carregarClientes();
    this.carregarClientes();
  }

  salvarCliente() {
    this.submitted = true;

    if (!this.cliente.nome?.trim()) {
        this.messageService.add({
            severity: 'warn',
            summary: 'Aviso',
            detail: 'O nome do cliente é obrigatório.',
            life: 3000,
        });
        return;
    }

    // Garantir que campos de telefone estejam corretos
    if (this.cliente.ddi1) this.cliente.ddi1 = parseInt(this.cliente.ddi1.toString());
    if (this.cliente.ddd1) this.cliente.ddd1 = parseInt(this.cliente.ddd1.toString());
    if (this.cliente.numero1) this.cliente.numero1 = parseInt(this.cliente.numero1.toString());

    if (this.cliente.ddi2) this.cliente.ddi2 = parseInt(this.cliente.ddi2.toString());
    if (this.cliente.ddd2) this.cliente.ddd2 = parseInt(this.cliente.ddd2.toString());
    if (this.cliente.numero2) this.cliente.numero2 = parseInt(this.cliente.numero2.toString());

    if (this.cliente.id) {
        this.clienteService.atualizarCliente(this.cliente).subscribe({
            next: () => {
                this.carregarClientes();
                this.carregarClientes();
                this.carregarClientes();
                this.carregarClientes();
                this.carregarClientes();
                this.messageService.add({
                    severity: 'success',
                    summary: 'Sucesso',
                    detail: 'Cliente atualizado com sucesso.',
                    life: 3000,
                });
                this.clienteDialog = false;
                this.cliente = {};
            },
            error: (err) => {
                console.error(err);
                this.carregarClientes();
                this.carregarClientes();
                this.carregarClientes();
                this.carregarClientes();
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erro',
                    detail: 'Erro ao atualizar cliente. Tente novamente mais tarde.',
                    life: 3000,
                });
            },
        });
    } else {
      this.clienteService.salvarCliente(this.cliente).subscribe({
        next: (response) => {
            // O corpo agora é uma string, por exemplo: "Cliente criado com sucesso."
            console.log("Resposta do servidor: ", response.body);
            
            if (response.status === 201) {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Sucesso',
                    detail: response.body?.toString() || 'Cliente criado com sucesso',  // Convertendo para string
                    life: 3000,
                });
                this.clienteDialog = false;
                this.cliente = {};
            } else {
                console.error("Erro inesperado: status " + response.status);
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erro',
                    detail: 'Erro ao criar cliente. Tente novamente mais tarde.',
                    life: 3000,
                });
            }
        },
        error: (err) => {
            console.error("Erro ao salvar cliente: ", err);
            setTimeout(() => {
              this.carregarClientes();
            }, 1000);
            
        },
    });
    
    
    }
    this.carregarClientes();
    this.clienteDialog = false;
    this.cliente = {};
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
      this.cliente.contato = '';
    } else if (this.cliente.tipoCliente === 'PessoaJuridica') {
      this.cliente.cpf = ''; // Limpar o CPF quando for Pessoa Jurídica
    }
  }
}
