import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { EstoqueService } from '../../services/estoque.service';
import { Peca } from '../models/Peca';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { RippleModule } from 'primeng/ripple';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FileUploadModule } from 'primeng/fileupload';
import { DropdownModule } from 'primeng/dropdown';
import { TagModule } from 'primeng/tag';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import { InputNumberModule } from 'primeng/inputnumber';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-estoque',
  standalone: true,
  templateUrl: './estoque.component.html',
  styleUrls: ['./estoque.component.css'],
  imports: [
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
  providers: [MessageService, ConfirmationService],
})
export class EstoqueComponent implements OnInit {
  pecaDialog: boolean = false;
  pecas: Peca[] = [];
  peca!: Peca;
  selectedPecas!: Peca[] | null;
  submitted: boolean = false;

  constructor(
    private estoqueService: EstoqueService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadPecas();
  }

  loadPecas(): void {
    this.estoqueService.getPecas().subscribe((pecas: Peca[]) => {
      this.pecas = pecas;
    });
  }

  openNew() {
    this.peca = {};
    this.submitted = false;
    this.pecaDialog = true;
  }

  deleteSelectedPecas() {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja deletar as peças selecionadas?',
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (this.selectedPecas && this.selectedPecas.length > 0) {
          const deleteRequests = this.selectedPecas
            .filter((peca) => peca.id !== undefined) 
            .map((peca) => this.estoqueService.deletePeca(peca.id!));
  
          if (deleteRequests.length > 0) {
            forkJoin(deleteRequests).subscribe({
              next: () => {
                this.loadPecas(); 
                this.selectedPecas = null;
                this.messageService.add({
                  severity: 'success',
                  summary: 'Sucesso',
                  detail: 'Peças deletadas com sucesso.',
                  life: 3000,
                });
              },
              error: (err) => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Erro',
                  detail: 'Falha ao deletar as peças.',
                  life: 3000,
                });
              },
            });
          } else {
            this.messageService.add({
              severity: 'warn',
              summary: 'Aviso',
              detail: 'Nenhuma peça válida para deletar.',
              life: 3000,
            });
          }
        }
      },
    });
  }


  editPeca(peca: Peca) {
    this.peca = { ...peca };
    this.pecaDialog = true;
  }

  deletePeca(peca: Peca) {
    console.log(peca)
    this.confirmationService.confirm({
        message: 'Are you sure you want to delete ' + peca.nome + '?',
        header: 'Confirm',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            if (peca.id !== undefined) {
                this.estoqueService.deletePeca(peca.id).subscribe(() => {
                    this.loadPecas();
                    this.peca = {};
                    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
                });
            }
        }
    });
}

  hideDialog() {
    this.pecaDialog = false;
    this.submitted = false;
  }

  savePeca() {
    this.submitted = true; 

    if (this.peca.nome?.trim()) {
      if (this.peca.id) {
        // Atualizar peça
        this.estoqueService.updatePeca(this.peca.id, this.peca).subscribe(() => {
          this.loadPecas();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Peça atualizada',
            life: 3000,
          });
        });
      } else {
        // Criar nova peça
        this.estoqueService.addPeca(this.peca).subscribe(() => {
          this.loadPecas();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Peça criada',
            life: 3000,
          });
        });
      }

      this.pecaDialog = false;
      this.peca = {};
    }
  }
}
