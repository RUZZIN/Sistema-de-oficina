import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from '../app/models/Cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private apiUrl = 'http://localhost:8080/api/clientes';  // URL da API

  // Injectando HttpClient no construtor
  constructor(private http: HttpClient) { }

  // Método para obter todos os clientes
  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.apiUrl);
  }

  // Método para buscar um cliente por ID
  getClienteById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`);
  }

// Método para salvar um novo cliente
salvarCliente(cliente: Cliente): Observable<HttpResponse<Cliente>> {
  return this.http.post<Cliente>(this.apiUrl, cliente, { observe: 'response' });
}

  // Método para atualizar um cliente existente
  atualizarCliente(cliente: Cliente): Observable<Cliente> {
    return this.http.put<Cliente>(`${this.apiUrl}/${cliente.id}`, cliente);
  }

  // Método para deletar um cliente
  deletarCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
