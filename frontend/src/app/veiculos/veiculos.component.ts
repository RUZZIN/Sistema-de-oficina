import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Veiculo } from '../models/Veiculo';
import { VeiculoService} from '../../services/veiculo.service';
import { CommonModule } from '@angular/common';
import { Marca } from '../models/Marca';
import { Modelo } from '../models/Modelo';
import { ModeloService } from '../../services/modelo.service';
import { MarcaService } from '../../services/marca.service';
import { DialogModule } from 'primeng/dialog'; // Importando o DialogModule
import { ButtonModule } from 'primeng/button'; // Para o botão

@Component({
  selector: 'app-veiculo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ButtonModule, DialogModule],
  template: `
     <div class="container">

  <!-- Botão para abrir o dialog -->
  <button pButton label="Adicionar Veículo" icon="pi pi-plus" (click)="openDialog()"></button>

  <!-- Dialog do formulário -->
  <p-dialog header="{{ editMode ? 'Editar Veículo' : 'Adicionar Veículo' }}" [(visible)]="displayDialog" [modal]="true" [closable]="false" [style]="{ width: '20%' }">
    <form [formGroup]="veiculoForm" (ngSubmit)="saveVeiculo()">
      <div>
        <label for="placa">Placa:</label> <br/>
        <input id="placa" formControlName="placa" />
      </div>

      <div>
        <label for="modelo">Modelo:</label> <br/>
        <select id="modelo" formControlName="modelo">
          <option *ngFor="let modelo of modelos" [value]="modelo.id">
            {{ modelo.nome }}
          </option>
        </select>
      </div>

      <div>
        <label for="marca">Marca:</label> <br/>
        <select id="marca" formControlName="marca">
          <option *ngFor="let marca of marcas" [value]="marca.id">
            {{ marca.nome }}
          </option>
        </select>
      </div>

      <div>
        <label for="quilometragem">Quilometragem:</label> <br/>
        <input id="quilometragem" formControlName="quilometragem" />
      </div>

      <div>
        <label for="chassi">Chassi:</label> <br/>
        <input id="chassi" formControlName="chassi" />
      </div>

      <div>
        <label for="patrimonio">Patrimônio:</label> <br/>
        <input id="patrimonio" formControlName="patrimonio" />
      </div>

      <div>
        <label for="anoModelo">Ano Modelo:</label> <br/>
        <input id="anoModelo" formControlName="anoModelo" />
      </div>

      <div>
        <label for="anoFabricacao">Ano Fabricação:</label> <br/>
        <input id="anoFabricacao" formControlName="anoFabricacao" />
      </div>
      <button pButton (click)="closeDialog()" label="Fechar"></button>
      <button pButton type="submit" label="{{ editMode ? 'Atualizar' : 'Salvar' }}"></button>
    </form>
  </p-dialog>

  <table>
    <thead>
      <tr>
        <th>Placa</th>
        <th>Modelo</th>
        <th>Marca</th>
        <th>Quilometragem</th>
        <th>Chassi</th>
        <th>Patrimônio</th>
        <th>Ações</th>
      </tr>
    </thead>
    <tbody>
      <tr *ngFor="let veiculo of veiculos">
        <td>{{ veiculo.placa }}</td>
        <td>{{ veiculo.idModelo?.nome }}</td>
        <td>{{ veiculo.idModelo?.idMarca?.nome }}</td>
        <td>{{ veiculo.quilometragem }}</td>
        <td>{{ veiculo.chassi }}</td>
        <td>{{ veiculo.patrimonio }}</td>
        <td>
          <button pButton icon="pi pi-pencil" (click)="editVeiculo(veiculo)"></button>
          <button pButton icon="pi pi-trash" (click)="deleteVeiculo(veiculo.placa!)"></button>
        </td>
      </tr>
    </tbody>
  </table>
</div>
  `,
  styleUrls: ['./veiculos.component.css']
})
export class VeiculoComponent implements OnInit {
  veiculos: Veiculo[] = [];
  modelos: Modelo[] = [];
  marcas: Marca[] = [];
  veiculoForm: FormGroup;
  editMode = false;
  selectedVeiculoId?: string;
  displayDialog: boolean = false; // Controle do dialog

  constructor(
    private veiculoService: VeiculoService,
    private marcaService: MarcaService,
    private modeloService: ModeloService,
    private formBuilder: FormBuilder
  ) {
    this.veiculoForm = this.formBuilder.group({
      placa: [''],
      modelo: [''],
      marca: [''],
      quilometragem: [0],
      chassi: [''],
      patrimonio: [''],
      anoModelo: [0],
      anoFabricacao: [0],
    });
  }

  ngOnInit(): void {
    this.loadVeiculos();
    this.loadMarcas();
  }

  loadVeiculos(): void {
    this.veiculoService.getVeiculos().subscribe({
      next: (data) => (this.veiculos = data),
      error: (err) => console.error('Erro ao carregar veículos:', err),
    });
  }

  loadMarcas(): void {
    this.marcaService.getMarcas().subscribe({
      next: (data) => {
        this.marcas = data;
        this.loadModelos();
      },
      error: (err) => console.error('Erro ao carregar marcas:', err),
    });
  }

  loadModelos(): void {
    this.modeloService.getModelos().subscribe({
      next: (data) => (this.modelos = data),
      error: (err) => console.error('Erro ao carregar modelos:', err),
    });
  }

  openDialog(): void {
    this.editMode = false;
    this.veiculoForm.reset();
    this.displayDialog = true;
  }

  saveVeiculo(): void {
    const veiculoFormValue = this.veiculoForm.value;

    const veiculo: Veiculo = {
      ...veiculoFormValue,
      idModelo: { id: veiculoFormValue.modelo },
    };

    if (this.editMode) {
      this.veiculoService.updateVeiculo(this.selectedVeiculoId!, veiculo).subscribe({
        next: () => {
          this.loadVeiculos();
          this.closeDialog();
        },
        error: (err) => console.error('Erro ao atualizar veículo:', err),
      });
    } else {
      this.veiculoService.addVeiculo(veiculo).subscribe({
        next: () => {
          this.loadVeiculos();
          this.closeDialog();
        },
        error: (err) => console.error('Erro ao adicionar veículo:', err),
      });
    }
  }

  editVeiculo(veiculo: Veiculo): void {
    this.editMode = true;
    this.selectedVeiculoId = veiculo.placa;
    this.veiculoForm.patchValue({
      ...veiculo,
      modelo: veiculo.idModelo?.id,
      marca: veiculo.idModelo?.idMarca?.id,
    });
    this.displayDialog = true;
  }

  deleteVeiculo(id: string): void {
    this.veiculoService.deleteVeiculo(id).subscribe({
      next: () => this.loadVeiculos(),
      error: (err) => console.error('Erro ao deletar veículo:', err),
    });
  }

  closeDialog(): void {
    this.displayDialog = false;
  }
}
