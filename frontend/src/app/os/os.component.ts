import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { OrdemServico } from '../models/Os';
import { OsService } from '../../services/os.service';
import { VeiculoService } from '../../services/veiculo.service';
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
import { forkJoin, Observable } from 'rxjs';
import { Cliente } from '../models/Cliente';
import { ClienteService } from '../../services/cliente.service';
import { jsPDF } from 'jspdf';


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
  ordemServicoDialogEditar: boolean = false;

  ordemServico: OrdemServico = {};
  selectedVeiculo: Veiculo | undefined;
  selectedCliente: Cliente | undefined;
  selectedServico: Servico | undefined;
  selectedFuncionario: Funcionario | undefined;
  selectedOss: OrdemServico[] | null = null;

  pecas: any[] = [];
  servicos: any[] = [];
  funcionarios: any[] = [];
  veiculos: any[] = [];
  clientes: any[] = [];
  itensPeca: ItensPeca[] = [];
  itensServico: ItensServico[] = [];


  statusOptions = [ "Orçamento", "Aprovação", "Execução","Finalizada", "Paga"];

  ordemServicoRequest: OrdemServicoRequest = {
    placaVeiculo: '',
    idCliente: 0,
    status: '',
    itensPeca: [],
    itensServico: []
  };
  


  
  constructor(
    private osService: OsService,
    private clienteService: ClienteService,
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
    this.loadClientes();
  }

  loadOss() {
    this.osService.getOss().subscribe((oss) => {
      this.oss = oss;  
    });
  }
  

  loadVeiculos() {
    this.veiculoService.getVeiculos().subscribe((data) => {
      this.veiculos = data
    });
  }
  loadClientes() {
    this.clienteService.getClientes().subscribe((data) => {
      this.clientes = data
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

  generatePdf(ordemServico: OrdemServico, placa: string) {
    if (!ordemServico.cliente?.id || !ordemServico.numero) {
      console.error('Cliente ou número de ordem de serviço não encontrado');
      return;
    }
  
    const cliente$ = this.clienteService.getClienteById(ordemServico.cliente.id);
    const veiculo$ = this.veiculoService.getVeiculoById(placa);
    const itensPeca$ = this.osService.getItensPecaByOs(ordemServico.numero);
    const itensServico$ = this.osService.getItensServicoByOs(ordemServico.numero);
  
    forkJoin([cliente$, veiculo$, itensPeca$, itensServico$]).subscribe(
      ([clienteRes, veiculoRes, itensPecaRes, itensServicoRes]) => {
        this.selectedCliente = clienteRes;
        this.selectedVeiculo = veiculoRes; // Todos os dados do veículo já estarão carregados aqui
        this.itensPeca = itensPecaRes || [];
        this.itensServico = itensServicoRes || [];
  
        // Gera o PDF com os dados carregados
        this.generatePdfDocument(ordemServico);
      },
      error => {
        console.error('Erro ao carregar os dados:', error);
      }
    );
  }
  
  

  generatePdfDocument(ordemServico: OrdemServico) {
    const doc = new jsPDF();
  
    // Cabeçalho
    doc.setFontSize(10);
    doc.text('MECANICA AutoGyn', 14, 10);
    doc.text('RUA 222 QD 98 LT 46', 14, 15);
    doc.text('Próximo à Paróquia São Francisco de Assis', 14, 20);
    doc.text('62 3095-1614 / 3092-8775', 14, 25);
    doc.text('mecanicautogyn17@gmail.com', 14, 30);
    doc.text(`GOIANIA, ${new Date().toLocaleDateString()}`, 14, 35);
  
    // Título e Ordem de Serviço
    doc.setFontSize(16);
    doc.setFont('helvetica', 'bold');
    doc.text('Orçamento da Ordem de Serviço', 14, 45);
    doc.setFontSize(12);
    doc.text(`Nº ${ordemServico.numero}`, 14, 50);
  
    // Adicionar Status
    doc.setFontSize(12);
    doc.setFont('helvetica', 'bold');
    doc.text('Status:'+ this.ordemServico.status, 14, 55);
  
    // Informações do Cliente
    let yPosition = 60; // Início da seção de cliente
    doc.setFontSize(10);
    doc.text('Cliente:', 14, yPosition);
    doc.setFont('helvetica', 'normal');
    yPosition += 5;
    doc.text(`Nome: ${this.selectedCliente?.nome || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(
      `Endereço: ${this.selectedCliente?.logradouro}, ${this.selectedCliente?.numero}, ${this.selectedCliente?.cidade}, ${this.selectedCliente?.estado}`,
      14,
      yPosition
    );
    yPosition += 5;
    if (this.selectedCliente?.cpf) {
      doc.text(`CPF: ${this.selectedCliente.cpf}`, 14, yPosition);
    } else if (this.selectedCliente?.cnpj) {
      doc.text(`CNPJ: ${this.selectedCliente.cnpj}`, 14, yPosition);
    } else {
      doc.text(`CPF/CNPJ: N/A`, 14, yPosition);
    }
    yPosition += 5;
    doc.text(`Telefone: ${this.selectedCliente?.numero1 || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(`Email: ${this.selectedCliente?.email || 'N/A'}`, 14, yPosition);
  
    // Informações do Veículo
    yPosition += 10; // Espaço entre cliente e veículo
    doc.setFont('helvetica', 'bold');
    doc.text('Veículo:', 14, yPosition);
    doc.setFont('helvetica', 'normal');
    yPosition += 5;
    doc.text(`Modelo: ${this.selectedVeiculo?.idModelo?.nome || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(`Marca: ${this.selectedVeiculo?.marca || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(`Placa: ${this.selectedVeiculo?.placa || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(`Quilometragem: ${this.selectedVeiculo?.quilometragem || 'N/A'} km`, 14, yPosition);
    yPosition += 5;
    doc.text(`Chassi: ${this.selectedVeiculo?.chassi || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(`Patrimônio: ${this.selectedVeiculo?.patrimonio || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(`Ano Modelo: ${this.selectedVeiculo?.anoModelo || 'N/A'}`, 14, yPosition);
    yPosition += 5;
    doc.text(`Ano Fabricação: ${this.selectedVeiculo?.anoFabricacao || 'N/A'}`, 14, yPosition);
  
    // Tabela de Peças
    yPosition += 10; // Espaço entre veículo e peças
    doc.setFont('helvetica', 'bold');
    doc.text('Peças a Substituir:', 14, yPosition);
    doc.setFont('helvetica', 'normal');
    yPosition += 5;
    this.itensPeca.forEach((item, index) => {
      doc.text(
        `${index + 1}. ${item.idPeca?.nome || 'N/A'} - Qtd: ${item.quantidade} - R$ ${item.precoTotal}`,
        14,
        yPosition
      );
      yPosition += 5;
    });
  
    // Tabela de Serviços
    yPosition += 10; // Espaço entre peças e serviços
    doc.setFont('helvetica', 'bold');
    doc.text('Serviços a Executar:', 14, yPosition);
    doc.setFont('helvetica', 'normal');
    yPosition += 5;
    this.itensServico.forEach((item, index) => {
      doc.text(
        `${index + 1}. ${item.idServico?.nome || 'N/A'} - R$ ${item.precoTotal}`,
        14,
        yPosition
      );
      yPosition += 5;
    });
  
    // Totais
    yPosition += 10; // Espaço antes dos totais
    doc.setFontSize(12);
    yPosition += 10;
    doc.setFont('helvetica', 'bold');
    doc.text(`Total: R$ ${this.ordemServico.precoFinal}`, 14, yPosition);
  
    // Salvar o PDF
    doc.save(`Ordem_Servico_${ordemServico.numero}.pdf`);
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
    this.selectedCliente = undefined;
    this.itensPeca = [];
    this.itensServico = [];
    this.ordemServicoDialog = true;
  }

  saveOs() {
    const requestPayload = {
      status: this.ordemServico.status,
      placaVeiculo: this.selectedVeiculo?.placa,
      idCliente: this.selectedCliente?.id,
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
  
    // Envia a solicitação para adicionar a ordem de serviço
    this.osService.addOs(requestPayload).subscribe((novaOs) => {
      // Atualiza a lista oss com a nova ordem de serviço. Se a resposta retornar o objeto da nova OS, usamos ele aqui.
      
  
      this.messageService.add({
        severity: 'success',
        summary: 'Sucesso',
        detail: 'Ordem de Serviço criada com sucesso!',
      });
  
      // Fecha o diálogo (caso o modal esteja sendo usado)
      this.hideDialog();
      
    });
    this.loadOss();
    this.loadOss();
    this.loadOss();
  }
  
  editOs(ordemServico: OrdemServico, placa: string) {
    this.ordemServico = { ...ordemServico };
    this.itensPeca = [];
    this.itensServico = [];
    
    const idCliente = ordemServico.cliente?.id;
    if (!idCliente) {
      console.error('Cliente não encontrado na ordem de serviço');
      return;
    }
    //trazer o cliente
    this.clienteService.getClienteById(idCliente).subscribe(
      (clienteData: Cliente) => {
        this.selectedCliente = clienteData;
        console.log("Cliente selecionado:", this.selectedCliente);
      },
      (error) => {
        console.error("Erro ao buscar cliente:", error);
      }
    );
     
      this.veiculoService.getVeiculoById(placa).subscribe(
        (veiculoData: Veiculo) => {
          this.selectedVeiculo = veiculoData;
          console.log("Veículo selecionado:", this.selectedVeiculo);
        },
        (error) => {
          console.error("Erro ao buscar veículo:", error);
        }
      );
    
    

    // Carregar os itens de peça da ordem de serviço
    if (this.ordemServico.numero) {
      this.osService.getItensPecaByOs(this.ordemServico.numero).subscribe((data) => {
        console.log("data:"+data);
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
        console.log("itensServico:"+itensServico);

        this.itensServico = itensServico.map((item) => ({
          id: item.id,
          horarioInicio: item.horarioInicio,
          horarioFim: item.horarioFim,
          quantidade: item.quantidade,
          idFuncionario: item.idFuncionario, 
          idServico: item.idServico,       
          precoTotal: item.precoTotal,
          numeroOs: item.numeroOs
        }));
      });
    }
    

    this.calcularTotal();
    // Abrir o diálogo para edição
    this.ordemServicoDialogEditar = true;
  }
  saveEdicao() {
    // Construir o payload para envio
    const requestPayload = {
      status: this.ordemServico.status,
      placaVeiculo: this.selectedVeiculo?.placa,
      idCliente: this.selectedCliente?.id,
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
  
  
    // Chamar o serviço para salvar alterações
    if (this.ordemServico.numero === undefined) {
      this.messageService.add({
        severity: 'error',
        summary: 'Erro',
        detail: 'Número da Ordem de Serviço não encontrado',
      });
      return;
    }

    this.osService
      .salvarAlteracoes(this.ordemServico.numero, requestPayload)
      .subscribe(() => {
        this.messageService.add({
          severity: 'success',
          summary: 'Sucesso',
          detail: 'Ordem de Serviço atualizada com sucesso!',
        });
        this.hideDialog();
      });
      this.loadOss();
      this.loadOss();
  }
  

  hideDialog() {
    this.ordemServicoDialog = false;
    this.ordemServicoDialogEditar = false;
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
  
    return totalPecas + totalServicos;
  }
  
}
