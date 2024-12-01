import { ItensPeca } from "../app/models/ItensPeca";
import { ItensServico } from "../app/models/ItensServico";

export interface OrdemServicoRequest {
    placaVeiculo: string;
    idCliente: number;
    status: string;
    itensPeca: ItensPeca[];
    itensServico: ItensServico[];
  }