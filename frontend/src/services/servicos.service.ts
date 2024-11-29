import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Servico } from '../app/models/Servico';

@Injectable({
  providedIn: 'root',
})
export class ServicosService {
  private apiUrl = 'http://localhost:8080/api/servicos';

  constructor(private httpClient: HttpClient) {}

  // Listar todas as peças
  getServicos(): Observable<Servico[]> {
    return this.httpClient.get<Servico[]>(this.apiUrl);
  }

  // Buscar uma peça por ID
  getServicoById(id: number): Observable<Servico> {
    return this.httpClient.get<Servico>(`${this.apiUrl}/${id}`);
  }

  // Adicionar nova peça
  addServico(servico: Servico): Observable<void> {
    return this.httpClient.post<void>(this.apiUrl, servico)
  }

  // Atualizar uma peça existente
  updateServico(id: number, servico: Servico): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl}/${id}`, servico);
  }

  // Deletar uma peça
  deleteServico(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }
}
