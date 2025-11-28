import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { SocioService } from '../../../services/socio.service';
import { Socio } from '../../../models/socio.model';

@Component({
  selector: 'app-socios-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="card">
      <div class="card-header">
        <h3 class="mb-0">{{ isEditing ? '✏️ Editar Socio' : '➕ Nuevo Socio' }}</h3>
      </div>
      <div class="card-body">
        <form (ngSubmit)="guardarSocio()" #socioForm="ngForm">
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="rut" class="form-label">RUT *</label>
              <input type="text" class="form-control" id="rut" name="rut" 
                     [(ngModel)]="socio.rut" required [disabled]="isEditing"
                     placeholder="12345678-9">
            </div>
            <div class="col-md-6 mb-3">
              <label for="nombres" class="form-label">Nombres *</label>
              <input type="text" class="form-control" id="nombres" name="nombres" 
                     [(ngModel)]="socio.nombres" required>
            </div>
          </div>

          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="apellidoPaterno" class="form-label">Apellido Paterno *</label>
              <input type="text" class="form-control" id="apellidoPaterno" name="apellidoPaterno" 
                     [(ngModel)]="socio.apellidoPaterno" required>
            </div>
            <div class="col-md-6 mb-3">
              <label for="apellidoMaterno" class="form-label">Apellido Materno *</label>
              <input type="text" class="form-control" id="apellidoMaterno" name="apellidoMaterno" 
                     [(ngModel)]="socio.apellidoMaterno" required>
            </div>
          </div>

          <div class="row">
            <div class="col-md-4 mb-3">
              <label for="genero" class="form-label">Género *</label>
              <select class="form-control" id="genero" name="genero" 
                      [(ngModel)]="socio.genero" required>
                <option value="">Seleccione...</option>
                <option value="Masculino">Masculino</option>
                <option value="Femenino">Femenino</option>
                <option value="Otro">Otro</option>
              </select>
            </div>
            <div class="col-md-4 mb-3">
              <label for="correo" class="form-label">Correo *</label>
              <input type="email" class="form-control" id="correo" name="correo" 
                     [(ngModel)]="socio.correo" required>
            </div>
            <div class="col-md-4 mb-3">
              <label for="celular" class="form-label">Celular *</label>
              <input type="text" class="form-control" id="celular" name="celular" 
                     [(ngModel)]="socio.celular" required placeholder="+56912345678">
            </div>
          </div>

          <div class="mb-3">
            <div class="form-check">
              <input class="form-check-input" type="checkbox" id="habilitado" name="habilitado" 
                     [(ngModel)]="socio.habilitado">
              <label class="form-check-label" for="habilitado">
                Socio Habilitado
              </label>
            </div>
          </div>

          <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-primary" [disabled]="!socioForm.valid || loading">
              {{ loading ? 'Guardando...' : (isEditing ? 'Actualizar' : 'Guardar') }}
            </button>
            <a routerLink="/socios" class="btn btn-secondary">Cancelar</a>
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
export class SociosFormComponent implements OnInit {
  socio: Socio = {
    rut: '',
    nombres: '',
    apellidoPaterno: '',
    apellidoMaterno: '',
    genero: '',
    correo: '',
    celular: '',
    habilitado: true
  };

  isEditing = false;
  loading = false;

  constructor(
    private socioService: SocioService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const rut = this.route.snapshot.paramMap.get('rut');
    if (rut) {
      this.isEditing = true;
      this.cargarSocio(rut);
    }
  }

  cargarSocio(rut: string): void {
    this.socioService.getById(rut).subscribe({
      next: (data) => {
        this.socio = data;
      },
      error: (err) => {
        console.error('Error al cargar socio:', err);
        alert('Error al cargar el socio');
        this.router.navigate(['/socios']);
      }
    });
  }

  guardarSocio(): void {
    this.loading = true;
    if (this.isEditing) {
      this.socioService.update(this.socio.rut, this.socio).subscribe({
        next: () => {
          alert('Socio actualizado correctamente');
          this.router.navigate(['/socios']);
        },
        error: (err) => {
          console.error('Error al actualizar socio:', err);
          alert('Error al actualizar el socio');
          this.loading = false;
        }
      });
    } else {
      this.socioService.create(this.socio).subscribe({
        next: () => {
          alert('Socio creado correctamente');
          this.router.navigate(['/socios']);
        },
        error: (err) => {
          console.error('Error al crear socio:', err);
          alert('Error al crear el socio');
          this.loading = false;
        }
      });
    }
  }
}

