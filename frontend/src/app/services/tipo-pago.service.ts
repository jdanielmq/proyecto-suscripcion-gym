import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TipoPago } from '../models/tipo-pago.model';

@Injectable({
  providedIn: 'root'
})
export class TipoPagoService {
  private apiUrl = 'http://localhost:8080/api/tipos-pago';

  constructor(private http: HttpClient) {}

  getAll(): Observable<TipoPago[]> {
    return this.http.get<TipoPago[]>(this.apiUrl);
  }

  getActivos(): Observable<TipoPago[]> {
    return this.http.get<TipoPago[]>(`${this.apiUrl}/activos`);
  }
}

