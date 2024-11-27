import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { OsService } from '../../services/os.service';
import { OrdemServico } from '../models/Os';
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
import { VeiculoService } from '../../services/VeiculoService.service';
import { Veiculo } from '../models/Veiculo';

@Component({
  selector: 'app-ordem-servico',
  templateUrl: './os.component.html',
  standalone: true,
  styleUrls: ['./os.component.css'],
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
export class OsComponent implements OnInit {
  oss: OrdemServico[] = [];
  veiculos: Veiculo[]= [];
  selectedOss: OrdemServico[] | null = null;
  ordemServicoDialog: boolean = false;
  ordemServico!: OrdemServico;
  submitted: boolean = false;
  selectedVeiculo: Veiculo | undefined;

  statusOptions = [
    { label: 'Orçamento', value: 'Orçamento' },
    { label: 'Aprovação', value: 'Aprovação' },
    { label: 'Execução', value: 'Execução' },
    { label: 'Finalizada', value: 'Finalizada' },
    { label: 'Paga', value: 'Paga' }
];


  constructor(
    private osService: OsService,
    private veiculoService: VeiculoService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.loadVeiculos;
    this.loadOss();
  }

  loadOss() {
    this.osService.getOss().subscribe((data) => {
      this.oss = data;
    });
  }

  loadVeiculos() {
    this.veiculoService.getVeiculos().subscribe((data) => {
      console.log(data)
      this.veiculos = data;
    });
  }

  


  openNew() {
    this.ordemServico = {};
    this.submitted = false;
    this.ordemServicoDialog = true;
    this.loadVeiculos();
  }

  deleteSelectedOss() {
    this.confirmationService.confirm({
      message: 'Tem certeza de que deseja excluir as Ordens de Serviço selecionadas?',
      header: 'Confirmação',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (this.selectedOss) {
          this.selectedOss.forEach((os) => {
            this.osService.deleteOs(os.numero!).subscribe(() => this.loadOss());
          });
        }
        this.selectedOss = null;
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Ordens de Serviço excluídas',
          life: 3000,
        });
      },
    });
  }

  editOs(ordemServico: OrdemServico) {
    this.ordemServico = { ...ordemServico };
    this.ordemServicoDialog = true;
  }

  deleteOs(ordemServico: OrdemServico) {
    this.confirmationService.confirm({
      message: `Tem certeza de que deseja excluir a Ordem de Serviço nº ${ordemServico.numero}?`,
      header: 'Confirmação',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.osService.deleteOs(ordemServico.numero!).subscribe(() => this.loadOss());
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Ordem de Serviço excluída',
          life: 3000,
        });
      },
    });
  }

  hideDialog() {
    this.ordemServicoDialog = false;
    this.submitted = false;
  }

  saveOs() {
    this.submitted = true;

    if (this.ordemServico.numero) {
      this.osService
        .updateOs(this.ordemServico.numero, this.ordemServico)
        .subscribe(() => {
          this.loadOss();
          this.messageService.add({
            severity: 'success',
            summary: 'Sucesso',
            detail: 'Ordem de Serviço atualizada',
            life: 3000,
          });
        });
    } else {
      this.osService.addOs(this.ordemServico).subscribe(() => {
        this.loadOss();
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Ordem de Serviço criada',
          life: 3000,
        });
      });
    }

    this.oss = [...this.oss];
    this.ordemServicoDialog = false;
    this.ordemServico = {};
  }
}
