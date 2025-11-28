import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Estado } from '../models/estado.model';

@Injectable({
  providedIn: 'root'
})
export class EstadoService {
  private apiUrl = 'http://localhost:8080/api/estados';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Estado[]> {
    return this.http.get<Estado[]>(this.apiUrl);
  }

  getHabilitados(): Observable<Estado[]> {
    return this.http.get<Estado[]>(`${this.apiUrl}/habilitados`);
  }
}

