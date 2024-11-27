import { Funcionario } from "./Funcionario";
import { OrdemServico } from "./Os";
import { Servico } from "./Servico";

export interface ItensServico {
    id: number;
    horarioInicio: string; // Usando string para representar horários no formato ISO ou 'HH:mm:ss'
    horarioFim: string;
    quantidade: number;
    precoTotal: number;
    idFuncionario: Funcionario; // Relacionamento com Funcionario
    idServico: Servico; // Relacionamento com Servico
    numeroOs: OrdemServico; // Relacionamento com OrdemServico
  }
  