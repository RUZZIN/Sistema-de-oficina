import { Modelo } from "./Modelo";

export class Veiculo {
  id?: number;
  placa?: string;
  quilometragem?: number;
  chassi?: string;
  patrimonio?: string;
  anoModelo?: number;
  anoFabricacao?: number;
  idModelo?: Modelo;
}
