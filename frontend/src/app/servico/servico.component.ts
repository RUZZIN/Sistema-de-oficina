// src/app/components/servico/servico.component.ts
import { Component, OnInit } from '@angular/core';
import { ServicoService } from '../../services/servico.service';
import { Servico } from '../models/Servico';
import { MessageService } from 'primeng/api';
import { ConfirmationService } from 'primeng/api';

import { NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';

import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { PanelModule } from 'primeng/panel';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { RippleModule } from 'primeng/ripple';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';


@Component({
  selector: 'app-servico',
  templateUrl: './servico.component.html',
  styleUrls: ['./servico.component.css'],
  standalone: true,
  imports: [
    PanelModule,
    CommonModule,
    TableModule,
    DialogModule,
    RippleModule,
    ButtonModule,
    ToastModule,
    ToolbarModule,
    ConfirmDialogModule,
    InputTextModule,
    InputTextareaModule,
    FileUploadModule,
    DropdownModule,
    TagModule,
    RadioButtonModule,
    RatingModule,
    FormsModule,
    InputNumberModule,
  ],
  providers: [MessageService, ConfirmationService]
})
export class ServicoComponent implements OnInit {
  servicos: Servico[] = [];
  servicoSelecionado: Servico = { id: 0, nome: '', precoUnitario: 0 };
  displayDialog: boolean = false;

  constructor(
    private servicoService: ServicoService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) { }

  ngOnInit() {
    this.listarTodos();
  }

  listarTodos() {
    this.servicoService.listarTodos().subscribe(
      data => {
        this.servicos = data;
      },
      error => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao carregar serviços.' });
      }
    );
  }

  salvarServico() {
    if (!this.servicoSelecionado.nome || !this.servicoSelecionado.precoUnitario) {
      this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Por favor, preencha todos os campos.' });
      return;
    }
  
    if (this.servicoSelecionado.id) {
      this.servicoService.atualizar(this.servicoSelecionado.id, this.servicoSelecionado).subscribe(
        () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Serviço atualizado com sucesso.' });
          this.listarTodos();
          this.displayDialog = false;
          this.servicoSelecionado = { id: 0, nome: '', precoUnitario: 0 }; // Limpar os dados
        },
        error => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao atualizar o serviço.' });
        }
      );
    } else {
      this.servicoService.salvar(this.servicoSelecionado).subscribe(
        () => {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Serviço salvo com sucesso.' });
          this.listarTodos();
          this.displayDialog = false;
          this.servicoSelecionado = { id: 0, nome: '', precoUnitario: 0 }; // Limpar os dados
        },
        error => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao salvar o serviço.' });
        }
      );
    }
  }
  

  editarServico(servico: Servico) {
    this.servicoSelecionado = { ...servico };
    this.displayDialog = true;
  }

  deletarServico(id: number) {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja excluir este serviço?',
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.servicoService.deletar(id).subscribe(
          () => {
            this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Serviço excluído com sucesso.' });
            this.listarTodos();
          },
          error => {
            this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir o serviço.' });
          }
        );
      }
    });
  }
  

  showDialogToAdd() {
    this.servicoSelecionado = { id: 0, nome: '', precoUnitario: 0 };
    this.displayDialog = true;
  }
}
