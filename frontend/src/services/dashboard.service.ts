import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private readonly apiUrl = 'http://localhost:8080/api/dashboard';

  constructor(private http: HttpClient) {}

  /**
   * Método para obter os dados do dashboard
   * @returns Observable com os dados do dashboard
   */
  getDashboardData(): Observable<DashboardData> {
    return this.http.get<DashboardData>(this.apiUrl);
  }
}

/**
 * Interface para tipar os dados do dashboard
 */
export interface DashboardData {
  totalOrdensDeServico: number;
  receitaTotal: number;
  funcionarioComMaisServicos: string;
  pecaMaisVendida: string;
  servicoMaisSolicitado: string;
  clienteComMaisOrdensDeServico: string;
  veiculoMaisAtendido: string;
}
