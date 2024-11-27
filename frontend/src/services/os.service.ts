import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrdemServico } from '../app/models/Os';

@Injectable({
  providedIn: 'root',
})
export class OsService {
  private apiUrl = 'http://localhost:8080/api/ordemServico  ';

  constructor(private httpClient: HttpClient) {}

  getOss(): Observable<OrdemServico[]> {
    return this.httpClient.get<OrdemServico[]>(this.apiUrl);
  }

  getOsById(id: number): Observable<OrdemServico> {
    return this.httpClient.get<OrdemServico>(`${this.apiUrl}/${id}`);
  }

  addOs(os: OrdemServico): Observable<OrdemServico> {
    return this.httpClient.post<OrdemServico>(this.apiUrl, os);
  }

  updateOs(id: number, os: OrdemServico): Observable<OrdemServico> {
    return this.httpClient.put<OrdemServico>(`${this.apiUrl}/${id}`, os);
  }

  deleteOs(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.apiUrl}/${id}`);
  }
}
