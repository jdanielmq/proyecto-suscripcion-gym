import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Suscripcion } from '../models/suscripcion.model';

@Injectable({
  providedIn: 'root'
})
export class SuscripcionService {
  private apiUrl = 'http://localhost:8080/api/suscripciones';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Suscripcion[]> {
    return this.http.get<Suscripcion[]>(this.apiUrl);
  }

  getById(id: number): Observable<Suscripcion> {
    return this.http.get<Suscripcion>(`${this.apiUrl}/${id}`);
  }

  create(suscripcion: Suscripcion): Observable<Suscripcion> {
    return this.http.post<Suscripcion>(this.apiUrl, suscripcion);
  }

  update(id: number, suscripcion: Suscripcion): Observable<Suscripcion> {
    return this.http.put<Suscripcion>(`${this.apiUrl}/${id}`, suscripcion);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getVigentes(): Observable<Suscripcion[]> {
    return this.http.get<Suscripcion[]>(`${this.apiUrl}/vigentes`);
  }
}

