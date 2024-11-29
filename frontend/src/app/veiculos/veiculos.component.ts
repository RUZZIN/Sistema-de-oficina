import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Veiculo } from '../models/Veiculo';
import { VeiculoService} from '../../services/veiculo.service';
import { CommonModule } from '@angular/common';
import { Marca } from '../models/Marca';
import { Modelo } from '../models/Modelo';
import { ModeloService } from '../../services/modelo.service';
import { MarcaService } from '../../services/marca.service';

@Component({
  selector: 'app-veiculo',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
     <div class="container">
  <h2>Gestão de Veículos</h2>

  <form [formGroup]="veiculoForm" (ngSubmit)="saveVeiculo()">
  <div>
    <label for="placa">Placa:</label>
    <input id="placa" formControlName="placa" />
  </div>

  <!-- Dropdown para selecionar o Modelo -->
  <div>
    <label for="modelo">Modelo:</label>
    <select id="modelo" formControlName="modelo">
      <option *ngFor="let modelo of modelos" [value]="modelo.id">
        {{ modelo.nome }}
      </option>
    </select>
  </div>

  <!-- Dropdown para selecionar a Marca -->
  <div>
    <label for="marca">Marca:</label>
    <select id="marca" formControlName="marca">
      <option *ngFor="let marca of marcas" [value]="marca.id">
        {{ marca.nome }}
      </option>
    </select>
  </div>

  <div>
    <label for="quilometragem">Quilometragem:</label>
    <input id="quilometragem" formControlName="quilometragem" />
  </div>

  <div>
    <label for="chassi">Chassi:</label>
    <input id="chassi" formControlName="chassi" />
  </div>

  <div>
    <label for="patrimonio">Patrimônio:</label>
    <input id="patrimonio" formControlName="patrimonio" />
  </div>

  <div>
    <label for="anoModelo">Ano Modelo:</label>
    <input id="anoModelo" formControlName="anoModelo" />
  </div>

  <div>
    <label for="anoFabricacao">Ano Fabricação:</label>
    <input id="anoFabricacao" formControlName="anoFabricacao" />
  </div>

  <button type="submit">{{ editMode ? 'Atualizar' : 'Salvar' }}</button>
</form>


  <h3>Lista de Veículos</h3>
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
        <td>{{ veiculo.idModelo?.nome }}</td> <!-- Nome do modelo associado -->
        <td>{{ veiculo.idModelo?.idMarca?.nome }}</td> <!-- Nome da marca através do idModelo -->
        <td>{{ veiculo.quilometragem }}</td>
        <td>{{ veiculo.chassi }}</td>
        <td>{{ veiculo.patrimonio }}</td>
        <td>
          <button (click)="editVeiculo(veiculo)">Editar</button>
          <button (click)="deleteVeiculo(veiculo.id!)">Deletar</button>
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
  selectedVeiculoId?: number;

  constructor(
    private veiculoService: VeiculoService,
    private marcaService: MarcaService,
    private modeloService: ModeloService,
    private formBuilder: FormBuilder
  ) {
    this.veiculoForm = this.formBuilder.group({
      id: [''],
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

  // Carregar os veículos
  loadVeiculos(): void {
    this.veiculoService.getVeiculos().subscribe({
      next: (data) => (this.veiculos = data),
      error: (err) => console.error('Erro ao carregar veículos:', err),
    });
  }

  // Carregar as marcas utilizando MarcaService
  loadMarcas(): void {
    this.marcaService.getMarcas().subscribe({
      next: (data) => {
        this.marcas = data;
        this.loadModelos(); // Após carregar marcas, carregamos os modelos
      },
      error: (err) => console.error('Erro ao carregar marcas:', err),
    });
  }

  // Carregar os modelos utilizando ModeloService
  loadModelos(): void {
    this.modeloService.getModelos().subscribe({
      next: (data) => (this.modelos = data),
      error: (err) => console.error('Erro ao carregar modelos:', err),
    });
  }

  // Salvar ou atualizar veículo
  saveVeiculo(): void {
    const veiculo: Veiculo = this.veiculoForm.value;

    if (this.editMode) {
      this.veiculoService.updateVeiculo(this.selectedVeiculoId!, veiculo).subscribe({
        next: () => {
          this.loadVeiculos();
          this.resetForm();
        },
        error: (err) => console.error('Erro ao atualizar veículo:', err),
      });
    } else {
      this.veiculoService.addVeiculo(veiculo).subscribe({
        next: () => {
          this.loadVeiculos();
          this.resetForm();
        },
        error: (err) => console.error('Erro ao adicionar veículo:', err),
      });
    }
  }

  // Editar veículo
  editVeiculo(veiculo: Veiculo): void {
    this.editMode = true;
    this.selectedVeiculoId = veiculo.id;
    this.veiculoForm.patchValue({
      ...veiculo,
      modelo: veiculo.idModelo?.id,
      marca: veiculo.idModelo?.idMarca?.id,
    });
  }

  // Deletar veículo
  deleteVeiculo(id: number): void {
    this.veiculoService.deleteVeiculo(id).subscribe({
      next: () => this.loadVeiculos(),
      error: (err) => console.error('Erro ao deletar veículo:', err),
    });
  }

  // Resetar o formulário
  resetForm(): void {
    this.editMode = false;
    this.selectedVeiculoId = undefined;
    this.veiculoForm.reset();
  }
}