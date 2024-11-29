import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Funcionario } from '../app/models/Funcionario';

@Injectable({
  providedIn: 'root',
})
export class FuncionariosService {
  private apiUrl = 'http://localhost:8080/api/funcionarios';

  constructor(private httpClient: HttpClient) {}

  // Listar todas as Funcionario
  getFuncionarios(): Observable<Funcionario[]> {
    return this.httpClient.get<Funcionario[]>(this.apiUrl);
  }

  // Buscar uma peça por ID
  getFuncionarioById(id: number): Observable<Funcionario> {
    return this.httpClient.get<Funcionario>(`${this.apiUrl}/${id}`);
  }

  // Adicionar nova peça
  addFuncionario(funcionario: Funcionario): Observable<void> {
    return this.httpClient.post<void>(this.apiUrl, funcionario)
  }

  // Atualizar uma peça existente
  updateFuncionario(id: number, funcionario: Funcionario): Observable<void> {
    return this.httpClient.put<void>(`${this.apiUrl}/${id}`, funcionario);
  }

  // Deletar uma peça
  deleteFuncionario(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }
}
