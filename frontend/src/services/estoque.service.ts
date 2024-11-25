import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Peca } from '../app/models/Peca';

@Injectable({
  providedIn: 'root',
})
export class EstoqueService {
  private apiUrl = 'http://localhost:8080/api/pecas';

  constructor(private httpClient: HttpClient) {}

  // Listar todas as peças
  getPecas(): Observable<Peca[]> {
    return this.httpClient.get<Peca[]>(this.apiUrl);
  }

  // Buscar uma peça por ID
  getPecaById(id: number): Observable<Peca> {
    return this.httpClient.get<Peca>(`${this.apiUrl}/${id}`);
  }

  // Adicionar nova peça
  addPeca(peca: Peca): Observable<void> {
    return this.httpClient.post<void>(this.apiUrl, peca);
  }

  // Atualizar uma peça existente
  updatePeca(id: number, peca: Peca): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl}/${id}`, peca);
  }

  // Deletar uma peça
  deletePeca(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }
}
