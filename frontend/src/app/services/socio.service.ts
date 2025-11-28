import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Socio } from '../models/socio.model';

@Injectable({
  providedIn: 'root'
})
export class SocioService {
  private apiUrl = 'http://localhost:8080/api/socios';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Socio[]> {
    return this.http.get<Socio[]>(this.apiUrl);
  }

  getById(rut: string): Observable<Socio> {
    return this.http.get<Socio>(`${this.apiUrl}/${rut}`);
  }

  create(socio: Socio): Observable<Socio> {
    return this.http.post<Socio>(this.apiUrl, socio);
  }

  update(rut: string, socio: Socio): Observable<Socio> {
    return this.http.put<Socio>(`${this.apiUrl}/${rut}`, socio);
  }

  delete(rut: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${rut}`);
  }

  getByHabilitado(habilitado: boolean): Observable<Socio[]> {
    return this.http.get<Socio[]>(`${this.apiUrl}/habilitado/${habilitado}`);
  }

  searchByNombre(nombre: string): Observable<Socio[]> {
    return this.http.get<Socio[]>(`${this.apiUrl}/buscar?nombre=${nombre}`);
  }
}

