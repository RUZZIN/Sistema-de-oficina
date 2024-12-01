// src/app/services/servico.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servico } from '../app/models/Servico';

@Injectable({
  providedIn: 'root'
})
export class ServicoService {
  private apiUrl = 'http://localhost:8080/api/servicos'; // URL do backend

  constructor(private http: HttpClient) { }

  listarTodos(): Observable<Servico[]> {
    return this.http.get<Servico[]>(this.apiUrl);
  }

  salvar(servico: Servico): Observable<any> {
    return this.http.post(this.apiUrl, servico);
  }

  atualizar(id: number, servico: Servico): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, servico);
  }

  deletar(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
