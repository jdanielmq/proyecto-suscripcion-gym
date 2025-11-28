import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SuscripcionService } from '../../../services/suscripcion.service';
import { Suscripcion } from '../../../models/suscripcion.model';

@Component({
  selector: 'app-suscripciones-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="card">
      <div class="card-header">
        <div class="d-flex justify-content-between align-items-center">
          <h3 class="mb-0">💳 Lista de Suscripciones</h3>
          <a routerLink="/suscripciones/nuevo" class="btn btn-primary">
            ➕ Nueva Suscripción
          </a>
        </div>
      </div>
      <div class="card-body">
        @if (loading) {
          <div class="text-center">
            <div class="spinner-border text-primary" role="status">
              <span class="visually-hidden">Cargando...</span>
            </div>
          </div>
        }
        @if (!loading && suscripciones.length === 0) {
          <div class="alert alert-info">
            No hay suscripciones registradas. Crea una nueva.
          </div>
        }
        @if (!loading && suscripciones.length > 0) {
          <div class="table-responsive">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Socio</th>
                  <th>Plan</th>
                  <th>Fecha Creación</th>
                  <th>Fecha Término</th>
                  <th>Monto Total</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                @for (suscripcion of suscripciones; track suscripcion.idSuscripcion) {
                  <tr>
                    <td>{{ suscripcion.idSuscripcion }}</td>
                    <td>{{ suscripcion.socio.nombres }} {{ suscripcion.socio.apellidoPaterno }}</td>
                    <td>{{ suscripcion.plan.tipoPlan }}</td>
                    <td>{{ suscripcion.fechaCreacion | date:'dd/MM/yyyy' }}</td>
                    <td>{{ suscripcion.fechaTermino | date:'dd/MM/yyyy' }}</td>
                    <td>\${{ calcularMontoTotal(suscripcion) | number:'1.0-0' }}</td>
                    <td>
                      <span [class]="getEstadoBadgeClass(suscripcion)">
                        {{ suscripcion.estado?.descripcion || 'N/A' }}
                      </span>
                    </td>
                    <td>
                      <a [routerLink]="['/suscripciones/editar', suscripcion.idSuscripcion]" 
                         class="btn btn-sm btn-warning me-2">
                        ✏️ Editar
                      </a>
                      <button (click)="eliminarSuscripcion(suscripcion.idSuscripcion!)" 
                              class="btn btn-sm btn-danger">
                        🗑️ Eliminar
                      </button>
                    </td>
                  </tr>
                }
              </tbody>
            </table>
          </div>
        }
      </div>
    </div>
  `,
  styles: [`
    .card-header h3 {
      color: white;
    }
  `]
})
export class SuscripcionesListComponent implements OnInit {
  suscripciones: Suscripcion[] = [];
  loading = true;

  constructor(private suscripcionService: SuscripcionService) {}

  ngOnInit(): void {
    this.cargarSuscripciones();
  }

  cargarSuscripciones(): void {
    this.loading = true;
    this.suscripcionService.getAll().subscribe({
      next: (data) => {
        this.suscripciones = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar suscripciones:', err);
        this.loading = false;
        alert('Error al cargar las suscripciones');
      }
    });
  }

  eliminarSuscripcion(id: number): void {
    if (confirm('¿Está seguro de eliminar esta suscripción?')) {
      this.suscripcionService.delete(id).subscribe({
        next: () => {
          alert('Suscripción eliminada correctamente');
          this.cargarSuscripciones();
        },
        error: (err) => {
          console.error('Error al eliminar suscripción:', err);
          alert('Error al eliminar la suscripción');
        }
      });
    }
  }

  calcularMontoTotal(suscripcion: Suscripcion): number {
    return (suscripcion.montoPlan || 0) + (suscripcion.montoMatricula || 0);
  }

  getEstadoBadgeClass(suscripcion: Suscripcion): string {
    const descripcion = suscripcion.estado?.descripcion?.toLowerCase() || '';
    if (descripcion.includes('activ')) return 'badge bg-success';
    if (descripcion.includes('vencid')) return 'badge bg-danger';
    if (descripcion.includes('suspendid')) return 'badge bg-warning';
    return 'badge bg-secondary';
  }
}

