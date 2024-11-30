import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modelo } from '../app/models/Modelo';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {
  private apiUrl = 'http://localhost:8080/modelos'; // URL base da API para modelos

  constructor(private http: HttpClient) { }

  // Listar todos os modelos
  getModelos(): Observable<Modelo[]> {
    return this.http.get<Modelo[]>(this.apiUrl);
  }

  // Buscar modelo por ID
  getModeloById(id: number): Observable<Modelo> {
    return this.http.get<Modelo>(`${this.apiUrl}/${id}`);
  }

  // Salvar novo modelo
  addModelo(modelo: Modelo): Observable<void> {
    return this.http.post<void>(this.apiUrl, modelo);
  }

  // Atualizar modelo existente
  updateModelo(id: number, modelo: Modelo): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}`, modelo);
  }

  // Deletar modelo por ID
  deleteModelo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
