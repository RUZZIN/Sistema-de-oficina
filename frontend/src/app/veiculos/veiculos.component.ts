import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { VeiculoService } from '../../services/Veiculo.service';
import { Veiculo } from '../models/Veiculo';
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
  selector: 'app-veiculos',
  standalone: true,
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
  templateUrl: './veiculos.component.html',
  styleUrl: './veiculos.component.css'
})
export class EstoqueComponent implements OnInit {
  veiculoDialog: boolean = false;
  veiculos: Veiculo[] = [];
  veiculo!: Veiculo;
  selectedveiculos!: Veiculo[] | null;
  submitted: boolean = false;

  constructor(
    private veiculoService: VeiculoService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadveiculos();
  }

  loadveiculos(): void {
    this.veiculoService.getVeiculos().subscribe((veiculos: Veiculo[]) => {
      this.veiculos = veiculos;
    });
  }

  openNew() {
    this.veiculo = {};
    this.submitted = false;
    this.veiculoDialog = true;
  }

  deleteSelectedveiculos() {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja deletar as peças selecionadas?',
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (this.selectedveiculos && this.selectedveiculos.length > 0) {
          const deleteRequests = this.selectedveiculos
            .filter((veiculo) => veiculo.id !== undefined) 
            .map((veiculo) => this.veiculoService.deleteveiculo(veiculo.id!));
  
          if (deleteRequests.length > 0) {
            forkJoin(deleteRequests).subscribe({
              next: () => {
                this.loadveiculos(); 
                this.selectedveiculos = null;
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
  

  editveiculo(veiculo: Veiculo) {
    this.veiculo = { ...veiculo };
    this.veiculoDialog = true;
  }

  deleteveiculo(veiculo: Veiculo) {
    console.log(veiculo)
    this.confirmationService.confirm({
        message: 'Are you sure you want to delete ' + veiculo.nome + '?',
        header: 'Confirm',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            if (veiculo.id !== undefined) {
                this.veiculoService.deleteveiculo(veiculo.id).subscribe(() => {
                    this.loadveiculos();
                    this.veiculo = {};
                    this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
                });
            }
        }
    });
  }

  hideDialog() {
    this.veiculoDialog = false;
    this.submitted = false;
  }

  saveveiculo() {
    this.submitted = true;

    if (this.veiculo.nome?.trim()) {
      if (this.veiculo.id) {
        // Atualizar peça
        this.veiculoService.updateveiculo(this.veiculo.id, this.veiculo).subscribe(() => {
          this.loadveiculos();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Peça atualizada',
            life: 3000,
          });
        });
      } else {
        // Criar nova peça
        this.veiculoService.addveiculo(this.veiculo).subscribe(() => {
          this.loadveiculos();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Peça criada',
            life: 3000,
          });
        });
      }

      this.veiculoDialog = false;
      this.veiculo = {};
    }
  }
}
