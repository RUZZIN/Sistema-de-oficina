import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { OrdemServico } from '../models/Os';
import { OsService } from '../../services/os.service';
import { VeiculoService } from '../../services/VeiculoService.service';
import { ServicosService } from '../../services/servicos.service';
import { FuncionariosService } from '../../services/funcionario.service';
import { Veiculo } from '../models/Veiculo';
import { Servico } from '../models/Servico';
import { Funcionario } from '../models/Funcionario';
import { EstoqueService } from '../../services/estoque.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { RadioButtonModule } from 'primeng/radiobutton';
import { RatingModule } from 'primeng/rating';
import { RippleModule } from 'primeng/ripple';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelModule } from 'primeng/panel';
import { OrdemServicoRequest } from '../../services/OrdemServicoRequest';
import { ItensPeca } from '../models/ItensPeca';
import { ItensServico } from '../models/ItensServico';
import { Peca } from '../models/Peca';

@Component({
  selector: 'app-ordem-servico',
  templateUrl: './os.component.html',
  standalone: true,
  styleUrls: ['./os.component.css'],
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
  providers: [MessageService, ConfirmationService],
})
export class OsComponent implements OnInit {
  oss: OrdemServico[] = [];
  ordemServicoDialog: boolean = false;
  ordemServico: OrdemServico = {};
  selectedVeiculo: Veiculo | undefined;
  selectedServico: Servico | undefined;
  selectedFuncionario: Funcionario | undefined;
  selectedOss: OrdemServico[] | null = null;

  pecas: any[] = [];
  servicos: any[] = [];
  funcionarios: any[] = [];
  veiculos: any[] = [];

  itensPeca: ItensPeca[] = [];
  itensServico: ItensServico[] = [];


  statusOptions = [ "Orçamento", "Aprovação", "Execução","Finalizada", "Paga"];

  ordemServicoRequest: OrdemServicoRequest = {
    placaVeiculo: '',
    status: '',
    itensPeca: [],
    itensServico: []
  };
  


  
  constructor(
    private osService: OsService,
    private veiculoService: VeiculoService,
    private estoqueService: EstoqueService,
    private servicoService: ServicosService,
    private funcionarioService: FuncionariosService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit() {
    this.loadVeiculos();
    this.loadPecas();
    this.loadServicos();
    this.loadOss()
    this.loadFuncionarios();
  }

  loadOss() {
    this.osService.getOss().subscribe((data) => {
      this.oss = data;
    });
  }

  loadVeiculos() {
    this.veiculoService.getVeiculos().subscribe((data) => {
      this.veiculos = data
    });
  }

  loadPecas() {
    this.estoqueService.getPecas().subscribe((data) => (this.pecas = data));
  }
  

  loadServicos() {
    this.servicoService.getServicos().subscribe((data) => (this.servicos = data));
  }

  loadFuncionarios() {
    this.funcionarioService.getFuncionarios().subscribe((data) => (this.funcionarios = data));
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'Orçamento':
        return 'orange'; // Cor para Orçamento
      case 'Aprovação':
        return 'blue'; // Cor para Aprovação
      case 'Execução':
        return 'yellow'; // Cor para Execução
      case 'Finalizada':
        return 'green'; // Cor para Finalizada
      case 'Paga':
        return 'gray'; // Cor para Paga
      default:
        return 'black'; // Cor padrão
    }
  }
  isDarkColor(color: string): boolean {
    // Verifique a cor de fundo e retorne true se for uma cor escura, senão false
    const darkColors = ['blue', 'green', 'gray']; // Defina as cores escuras
    return darkColors.includes(color.toLowerCase());
  }
    

  openNew() {
    this.ordemServico = {};
    this.selectedVeiculo = undefined;
    this.itensPeca = [];
    this.itensServico = [];
    this.ordemServicoDialog = true;
  }

  saveOs() {

    const requestPayload = {
      status: this.ordemServico.status, 
      placaVeiculo: this.selectedVeiculo?.placa, 

      itensPeca: this.itensPeca.map((item) => ({
        quantidade: item.quantidade,
        idPeca: item.idPeca.id,
      })),
      itensServico: this.itensServico.map((item) => ({
        horarioInicio: item.horarioInicio,
        horarioFim: item.horarioFim,
        quantidade: item.quantidade,
        idFuncionario: item.idFuncionario.id,
        idServico: item.idServico.id,
      })),
    };

    

    this.osService.addOs(requestPayload).subscribe(() => {
      this.messageService.add({
        severity: 'success',
        summary: 'Sucesso',
        detail: 'Ordem de Serviço criada com sucesso!',
      });
      this.hideDialog();
    });
  }

  editOs(ordemServico: OrdemServico) {
    this.ordemServico = { ...ordemServico };
  
    // Carregar o veículo selecionado
    this.selectedVeiculo = this.veiculos.find(
      (veiculo) => veiculo.placa === this.ordemServico.placaVeiculo
    );
  
    // Carregar os itens de peça da ordem de serviço
    if (this.ordemServico.numero) {
      this.osService.getItensPecaByOs(this.ordemServico.numero).subscribe((data) => {
        this.itensPeca = data.map((item) => ({
          id: item.id,
          quantidade: item.quantidade,
          idPeca: this.pecas.find((peca) => peca.id === item.idPeca.id), // Relacionar ao objeto completo
          precoTotal: item.precoTotal,
          numeroOs: item.numeroOs
        }));
      });
    }
    

    // Carregar os itens de serviço da ordem de serviço
    if (this.ordemServico.numero) {
      this.osService.getItensServicoByOs(this.ordemServico.numero).subscribe((itensServico) => {
        this.itensServico = itensServico.map((item) => ({
          id: item.id,
          horarioInicio: item.horarioInicio,
          horarioFim: item.horarioFim,
          quantidade: item.quantidade,
          idFuncionario: item.idFuncionario, // Apenas o ID
          idServico: item.idServico,         // Apenas o ID
          precoTotal: item.precoTotal,
          numeroOs: item.numeroOs
        }));
        console.log(this.itensServico);

      });
    }
    

    this.calcularTotal();
    // Abrir o diálogo para edição
    this.ordemServicoDialog = true;
  }
  
  
  

  hideDialog() {
    this.ordemServicoDialog = false;
  }

  addItemPeca() {
    this.itensPeca.push({
      idPeca: {} as Peca, quantidade: 1,
      id: 0,
      precoTotal: 0,
      numeroOs: new OrdemServico
    });
  }

  removeItemPeca(item: any) {
    this.itensPeca = this.itensPeca.filter((i) => i !== item);
  }

  addItemServico() {
    this.itensServico.push({
      idServico: {} as Servico,
      idFuncionario: {} as Funcionario,
      quantidade: 1,
      horarioInicio: '',
      horarioFim: '',
      id: 0,
      precoTotal: 0,
      numeroOs: new OrdemServico
    });
  }

  removeItemServico(item: any) {
    this.itensServico = this.itensServico.filter((i) => i !== item);
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



  calcularTotal(): number {
    let totalPecas = 0;
    let totalServicos = 0;
  
    // Calcular o total das peças
    this.itensPeca.forEach(item => {
      const peca = this.pecas.find(peca => peca.id === item.idPeca?.id);
      if (peca) {
        // Verificar se a quantidade solicitada não excede a quantidade no estoque
        const quantidadeEstoque = peca.quantidade;
        if (item.quantidade > quantidadeEstoque) {
          this.messageService.add({
            severity: 'error',
            summary: 'Erro',
            detail: `Quantidade de peça ${peca.nome} insuficiente no estoque.`
          });
          return;  // Retorna sem somar o valor caso a quantidade seja inválida
        }
        
        // Calcular o preço total das peças
        totalPecas += peca.precoUnitario * item.quantidade;
      }
    });
  
    // Calcular o total dos serviços
    this.itensServico.forEach(item => {
      const servico = this.servicos.find(servico => servico.id === item.idServico?.id);
      if (servico) {
        // Calcular o preço total dos serviços
        totalServicos += servico.precoUnitario * item.quantidade;
      }
    });
  
    // Retorna o preço total acumulado
    return totalPecas + totalServicos;
  }
  
}
