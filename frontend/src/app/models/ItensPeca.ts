import { OrdemServico } from "./Os";
import { Peca } from "./Peca";

export interface ItensPeca {
    id: number;
    precoTotal: number;
    quantidade: number;
    numeroOs: OrdemServico; // Relacionamento com OrdemServico
    idPeca: Peca; // Relacionamento com Pecas
  }
  