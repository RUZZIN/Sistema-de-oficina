import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Marca } from '../app/models/Marca';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {
  private apiUrl = 'http://localhost:8080/api/marcas'; // URL base da API para marcas

  constructor(private http: HttpClient) { }

  // Listar todas as marcas
  getMarcas(): Observable<Marca[]> {
    return this.http.get<Marca[]>(this.apiUrl);
  }

  // Buscar marca por ID
  gerMarcaById(id: number): Observable<Marca> {
    return this.http.get<Marca>(`${this.apiUrl}/${id}`);
  }

  // Salvar nova marca
  addMarca(marca: Marca): Observable<void> {
    return this.http.post<void>(this.apiUrl, marca);
  }

  // Atualizar marca existente
  updateMarca(id: number, marca: Marca): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, marca);
  }

  // Deletar marca por ID
  deleteMarca(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
