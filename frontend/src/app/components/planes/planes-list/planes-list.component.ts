import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlanService } from '../../../services/plan.service';
import { Plan } from '../../../models/plan.model';

@Component({
  selector: 'app-planes-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card">
      <div class="card-header">
        <h3 class="mb-0">📋 Lista de Planes</h3>
      </div>
      <div class="card-body">
        @if (loading) {
          <div class="text-center">
            <div class="spinner-border text-primary" role="status">
              <span class="visually-hidden">Cargando...</span>
            </div>
          </div>
        }
        @if (!loading && planes.length === 0) {
          <div class="alert alert-info">
            No hay planes registrados.
          </div>
        }
        @if (!loading && planes.length > 0) {
          <div class="row">
            @for (plan of planes; track plan.idPlan) {
              <div class="col-md-4 col-lg-3 mb-4 fade-in">
                <div class="card plan-card h-100">
                  <div class="card-header">
                    <h5 class="card-title">{{ plan.tipoPlan }}</h5>
                  </div>
                  <div class="card-body">
                    <div class="card-price">
                      \${{ plan.montoPlan | number:'1.0-0' }}
                    </div>
                    <div class="plan-feature">
                      <i class="bi bi-calendar-check"></i>
                      <span><strong>{{ plan.duracion }}</strong> {{ plan.unidad }}</span>
                    </div>
                    <div class="plan-feature">
                      <i class="bi bi-award"></i>
                      @if (plan.isMatricula) {
                        <span><strong>Matrícula:</strong> \${{ plan.montoMatricula | number:'1.0-0' }}</span>
                      } @else {
                        <span><strong>Sin matrícula</strong></span>
                      }
                    </div>
                    <div class="plan-feature">
                      <i class="bi bi-check-circle-fill"></i>
                      <span>Acceso completo</span>
                    </div>
                  </div>
                </div>
              </div>
            }
          </div>
        }
      </div>
    </div>
  `,
  styles: [`
    .card-header h3 {
      color: white;
    }
    .card.border-primary {
      border-width: 2px !important;
    }
  `]
})
export class PlanesListComponent implements OnInit {
  planes: Plan[] = [];
  loading = true;

  constructor(private planService: PlanService) {}

  ngOnInit(): void {
    this.cargarPlanes();
  }

  cargarPlanes(): void {
    this.loading = true;
    this.planService.getAll().subscribe({
      next: (data) => {
        this.planes = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error al cargar planes:', err);
        this.loading = false;
        alert('Error al cargar los planes');
      }
    });
  }
}

