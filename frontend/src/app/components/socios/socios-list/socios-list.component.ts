import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SocioService } from '../../../services/socio.service';
import { Socio } from '../../../models/socio.model';

@Component({
  selector: 'app-socios-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="card">
      <div class="card-header">
        <div class="d-flex justify-content-between align-items-center">
          <h3 class="mb-0">📋 Lista de Socios</h3>
          <a routerLink="/socios/nuevo" class="btn btn-primary">
            ➕ Nuevo Socio
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
        @if (!loading && socios.length === 0) {
          <div class="alert alert-info">
            No hay socios registrados. Crea uno nuevo.
          </div>
        }
        @if (!loading && socios.length > 0) {
          <div class="table-responsive">
            <table class="table table-hover">
              <thead>
                <tr>
                  <th>RUT</th>
                  <th>Nombre Completo</th>
                  <th>Género</th>
                  <th>Correo</th>
                  <th>Celular</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                @for (socio of socios; track socio.rut) {
                  <tr>
                    <td>{{ socio.rut }}</td>
                    <td>{{ socio.nombres }} {{ socio.apellidoPaterno }} {{ socio.apellidoMaterno }}</td>
                    <td>{{ socio.genero }}</td>
                    <td>{{ socio.correo }}</td>
                    <td>{{ socio.celular }}</td>
                    <td>
                      <span [class]="socio.habilitado ? 'badge bg-success' : 'badge bg-danger'">
                        {{ socio.habilitado ? 'Activo' : 'Inactivo' }}
                      </span>
                    </td>
                    <td>
                      <a [routerLink]="['/socios/editar', socio.rut]" class="btn btn-sm btn-warning me-2">
                        ✏️ Editar
                      </a>
                      <button (click)="eliminarSocio(socio.rut)" class="btn btn-sm btn-danger">
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
export class SociosListComponent implements OnInit {
  socios: Socio[] = [];
  loading = true;

  constructor(private socioService: SocioService) {}

  ngOnInit(): void {
    this.cargarSocios();
  }

  cargarSocios(): void {
    this.loading = true;
    this.socioService.getAll().subscribe({
      next: (data) => {
        this.socios = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar socios:', err);
        this.loading = false;
        alert('Error al cargar los socios');
      }
    });
  }

  eliminarSocio(rut: string): void {
    if (confirm('¿Está seguro de eliminar este socio?')) {
      this.socioService.delete(rut).subscribe({
        next: () => {
          alert('Socio eliminado correctamente');
          this.cargarSocios();
        },
        error: (err) => {
          console.error('Error al eliminar socio:', err);
          alert('Error al eliminar el socio');
        }
      });
    }
  }
}

