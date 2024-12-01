import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrdemServico } from '../app/models/Os';
import { ItensPeca } from '../app/models/ItensPeca'; // Modelo de ItensPeca
import { ItensServico } from '../app/models/ItensServico'; // Modelo de ItensServico

@Injectable({
  providedIn: 'root',
})
export class OsService {
  private apiUrl = 'http://localhost:8080/api/ordemServico';

  // Se necessário, você pode adicionar cabeçalhos de autorização aqui
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      // 'Authorization': 'Bearer ' + localStorage.getItem('token') // exemplo de cabeçalho de token
    })
  };

  constructor(private httpClient: HttpClient) {}

  // Método para buscar todas as ordens de serviço
  getOss(): Observable<OrdemServico[]> {
    return this.httpClient.get<OrdemServico[]>(this.apiUrl, this.httpOptions);
  }

  // Método para buscar uma ordem de serviço por ID
  getOsById(id: number): Observable<OrdemServico> {
    return this.httpClient.get<OrdemServico>(`${this.apiUrl}/${id}`, this.httpOptions);
  }

  // Método para adicionar uma nova ordem de serviço
  addOs(os: OrdemServico): Observable<OrdemServico> {
    return this.httpClient.post<OrdemServico>(this.apiUrl, os, this.httpOptions);
  }

  salvarAlteracoes(numero: number, os: OrdemServico): Observable<OrdemServico> {
    const url = `${this.apiUrl}/${numero}`; // Adicionar o número na URL
    return this.httpClient.put<OrdemServico>(url, os, this.httpOptions);
  }
  
  // Método para atualizar uma ordem de serviço existente
  updateOs(id: number, os: OrdemServico): Observable<OrdemServico> {
    return this.httpClient.put<OrdemServico>(`${this.apiUrl}/${id}`, os, this.httpOptions);
  }

  // Método para deletar uma ordem de serviço
  deleteOs(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`, this.httpOptions);
  }

  // Método para buscar os itens de peça relacionados a uma ordem de serviço
  getItensPecaByOs(numeroOs: number): Observable<ItensPeca[]> {
    return this.httpClient.get<ItensPeca[]>(`${this.apiUrl}/${numeroOs}/itensPeca`, this.httpOptions);
  }

  // Método para buscar os itens de serviço relacionados a uma ordem de serviço
  getItensServicoByOs(numeroOs: number): Observable<ItensServico[]> {
    return this.httpClient.get<ItensServico[]>(`${this.apiUrl}/${numeroOs}/itensServico`, this.httpOptions);
  }

  // Método para atualizar itens de peça
  updateItemPeca(id: number, itemPeca: ItensPeca): Observable<ItensPeca> {
    return this.httpClient.put<ItensPeca>(`${this.apiUrl}/itensPeca/${id}`, itemPeca, this.httpOptions);
  }

  // Método para adicionar item de peça
  addItemPeca(itemPeca: ItensPeca): Observable<ItensPeca> {
    return this.httpClient.post<ItensPeca>(`${this.apiUrl}/itensPeca`, itemPeca, this.httpOptions);
  }

  // Método para excluir um item de peça
  deleteItemPeca(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/itensPeca/${id}`, this.httpOptions);
  }

  // Método para atualizar itens de serviço
  updateItemServico(id: number, itemServico: ItensServico): Observable<ItensServico> {
    return this.httpClient.put<ItensServico>(`${this.apiUrl}/itensServico/${id}`, itemServico, this.httpOptions);
  }

  // Método para adicionar item de serviço
  addItemServico(itemServico: ItensServico): Observable<ItensServico> {
    return this.httpClient.post<ItensServico>(`${this.apiUrl}/itensServico`, itemServico, this.httpOptions);
  }

  // Método para excluir um item de serviço
  deleteItemServico(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/itensServico/${id}`, this.httpOptions);
  }
}
