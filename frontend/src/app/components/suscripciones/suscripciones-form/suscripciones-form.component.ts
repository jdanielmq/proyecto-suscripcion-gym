import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { SuscripcionService } from '../../../services/suscripcion.service';
import { SocioService } from '../../../services/socio.service';
import { PlanService } from '../../../services/plan.service';
import { TipoPagoService } from '../../../services/tipo-pago.service';
import { EstadoService } from '../../../services/estado.service';
import { Suscripcion } from '../../../models/suscripcion.model';
import { Socio } from '../../../models/socio.model';
import { Plan } from '../../../models/plan.model';
import { TipoPago } from '../../../models/tipo-pago.model';
import { Estado } from '../../../models/estado.model';

@Component({
  selector: 'app-suscripciones-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="card">
      <div class="card-header">
        <h3 class="mb-0">{{ isEditing ? '✏️ Editar Suscripción' : '➕ Nueva Suscripción' }}</h3>
      </div>
      <div class="card-body">
        <form (ngSubmit)="guardarSuscripcion()" #suscripcionForm="ngForm">
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="socio" class="form-label">Socio *</label>
              <select class="form-control" id="socio" name="socio" 
                      [(ngModel)]="socioSeleccionado" required>
                <option value="">Seleccione un socio...</option>
                @for (socio of socios; track socio.rut) {
                  <option [value]="socio.rut">
                    {{ socio.nombres }} {{ socio.apellidoPaterno }} ({{ socio.rut }})
                  </option>
                }
              </select>
            </div>
            <div class="col-md-6 mb-3">
              <label for="plan" class="form-label">Plan *</label>
              <select class="form-control" id="plan" name="plan" 
                      [(ngModel)]="planSeleccionado" (change)="onPlanChange()" required>
                <option value="">Seleccione un plan...</option>
                @for (plan of planes; track plan.idPlan) {
                  <option [value]="plan.idPlan">
                    {{ plan.tipoPlan }} - \${{ plan.montoPlan | number:'1.0-0' }}
                  </option>
                }
              </select>
            </div>
          </div>

          <div class="row">
            <div class="col-md-4 mb-3">
              <label for="tipoPago" class="form-label">Tipo de Pago *</label>
              <select class="form-control" id="tipoPago" name="tipoPago" 
                      [(ngModel)]="tipoPagoSeleccionado" required>
                <option value="">Seleccione...</option>
                @for (tipoPago of tiposPago; track tipoPago.idPago) {
                  <option [value]="tipoPago.idPago">
                    {{ tipoPago.descripcion }}
                  </option>
                }
              </select>
            </div>
            <div class="col-md-4 mb-3">
              <label for="nroTransaccion" class="form-label">Nro. Transacción *</label>
              <input type="text" class="form-control" id="nroTransaccion" name="nroTransaccion" 
                     [(ngModel)]="nroTransaccion" required placeholder="TRX-12345">
            </div>
            <div class="col-md-4 mb-3">
              <label for="nroCuotas" class="form-label">Nro. Cuotas</label>
              <input type="number" class="form-control" id="nroCuotas" name="nroCuotas" 
                     [(ngModel)]="nroCuotas" min="1">
            </div>
          </div>

          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="montoPlan" class="form-label">Monto Plan *</label>
              <input type="number" class="form-control" id="montoPlan" name="montoPlan" 
                     [(ngModel)]="montoPlan" required min="0">
              <small class="form-text text-muted">Se completa automáticamente al seleccionar el plan</small>
            </div>
            <div class="col-md-6 mb-3">
              <label for="montoMatricula" class="form-label">Monto Matrícula *</label>
              <input type="number" class="form-control" id="montoMatricula" name="montoMatricula" 
                     [(ngModel)]="montoMatricula" required min="0">
              <small class="form-text text-muted">Se completa automáticamente al seleccionar el plan</small>
            </div>
          </div>

          @if (isEditing) {
            <div class="mb-3">
              <label for="estado" class="form-label">Estado *</label>
              <select class="form-control" id="estado" name="estado" 
                      [(ngModel)]="estadoSeleccionado" required>
                @for (estado of estados; track estado.idEstado) {
                  <option [value]="estado.idEstado">
                    {{ estado.descripcion }}
                  </option>
                }
              </select>
            </div>
          }

          <div class="alert alert-info">
            <strong>Monto Total:</strong> \${{ (montoPlan + montoMatricula) | number:'1.0-0' }}
          </div>

          <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-primary" [disabled]="!suscripcionForm.valid || loading">
              {{ loading ? 'Guardando...' : (isEditing ? 'Actualizar' : 'Guardar') }}
            </button>
            <a routerLink="/suscripciones" class="btn btn-secondary">Cancelar</a>
          </div>
        </form>
      </div>
    </div>
  `,
  styles: [`
    .card-header h3 {
      color: white;
    }
  `]
})
export class SuscripcionesFormComponent implements OnInit {
  socios: Socio[] = [];
  planes: Plan[] = [];
  tiposPago: TipoPago[] = [];
  estados: Estado[] = [];

  socioSeleccionado = '';
  planSeleccionado: number | '' = '';
  tipoPagoSeleccionado: number | '' = '';
  estadoSeleccionado: number | '' = '';
  nroTransaccion = '';
  nroCuotas: number | null = null;
  montoPlan = 0;
  montoMatricula = 0;

  isEditing = false;
  loading = false;
  suscripcionId: number | null = null;

  constructor(
    private suscripcionService: SuscripcionService,
    private socioService: SocioService,
    private planService: PlanService,
    private tipoPagoService: TipoPagoService,
    private estadoService: EstadoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.cargarCatalogos();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditing = true;
      this.suscripcionId = parseInt(id);
      this.cargarSuscripcion(this.suscripcionId);
    }
  }

  cargarCatalogos(): void {
    this.socioService.getByHabilitado(true).subscribe(data => this.socios = data);
    this.planService.getAll().subscribe(data => this.planes = data);
    this.tipoPagoService.getActivos().subscribe(data => this.tiposPago = data);
    this.estadoService.getHabilitados().subscribe(data => this.estados = data);
  }

  cargarSuscripcion(id: number): void {
    this.suscripcionService.getById(id).subscribe({
      next: (data) => {
        this.socioSeleccionado = data.socio.rut;
        this.planSeleccionado = data.plan.idPlan!;
        this.tipoPagoSeleccionado = data.tipoPago.idPago!;
        this.estadoSeleccionado = data.estado?.idEstado || '';
        this.nroTransaccion = data.nroTransaccion;
        this.nroCuotas = data.nroCuotas || null;
        this.montoPlan = data.montoPlan || 0;
        this.montoMatricula = data.montoMatricula || 0;
      },
      error: (err) => {
        console.error('Error al cargar suscripción:', err);
        alert('Error al cargar la suscripción');
        this.router.navigate(['/suscripciones']);
      }
    });
  }

  onPlanChange(): void {
    // Convertir a número si viene como string
    const planId = typeof this.planSeleccionado === 'string' ? 
                   parseInt(this.planSeleccionado) : 
                   this.planSeleccionado;
    
    const plan = this.planes.find(p => p.idPlan === planId);
    if (plan) {
      this.montoPlan = plan.montoPlan || 0;
      this.montoMatricula = plan.isMatricula ? (plan.montoMatricula || 0) : 0;
      console.log('Plan seleccionado:', plan);
      console.log('Monto Plan:', this.montoPlan);
      console.log('Monto Matrícula:', this.montoMatricula);
    } else {
      console.warn('No se encontró el plan con ID:', planId);
    }
  }

  guardarSuscripcion(): void {
    this.loading = true;
    
    console.log('=== GUARDAR SUSCRIPCIÓN ===');
    console.log('Socio seleccionado:', this.socioSeleccionado);
    console.log('Plan seleccionado:', this.planSeleccionado);
    console.log('Tipo Pago seleccionado:', this.tipoPagoSeleccionado);
    
    // Validar que se hayan seleccionado los datos requeridos
    if (!this.socioSeleccionado || !this.planSeleccionado || !this.tipoPagoSeleccionado || !this.nroTransaccion) {
      alert('Por favor complete todos los campos obligatorios');
      this.loading = false;
      return;
    }
    
    // Solo enviar los IDs, no los objetos completos
    const suscripcion: Suscripcion = {
      fechaCreacion: new Date().toISOString(),
      nroTransaccion: this.nroTransaccion,
      tipoPago: { idPago: Number(this.tipoPagoSeleccionado) } as any,
      plan: { idPlan: Number(this.planSeleccionado) } as any,
      montoPlan: Number(this.montoPlan),
      montoMatricula: Number(this.montoMatricula),
      nroCuotas: this.nroCuotas ? Number(this.nroCuotas) : undefined,
      socio: { rut: this.socioSeleccionado } as any
    };
    
    console.log('Suscripción a enviar:', JSON.stringify(suscripcion, null, 2));

    if (this.isEditing && this.suscripcionId) {
      suscripcion.idSuscripcion = this.suscripcionId;
      const estado = this.estados.find(e => e.idEstado === this.estadoSeleccionado);
      if (estado) suscripcion.estado = estado;
      
      this.suscripcionService.update(this.suscripcionId, suscripcion).subscribe({
        next: () => {
          alert('Suscripción actualizada correctamente');
          this.router.navigate(['/suscripciones']);
        },
        error: (err) => {
          console.error('Error al actualizar suscripción:', err);
          alert('Error al actualizar la suscripción');
          this.loading = false;
        }
      });
    } else {
      this.suscripcionService.create(suscripcion).subscribe({
        next: () => {
          alert('Suscripción creada correctamente');
          this.router.navigate(['/suscripciones']);
        },
        error: (err) => {
          console.error('Error al crear suscripción:', err);
          alert('Error al crear la suscripción');
          this.loading = false;
        }
      });
    }
  }
}

