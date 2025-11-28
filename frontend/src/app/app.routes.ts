import { Routes } from '@angular/router';
import { SociosListComponent } from './components/socios/socios-list/socios-list.component';
import { SociosFormComponent } from './components/socios/socios-form/socios-form.component';
import { SuscripcionesListComponent } from './components/suscripciones/suscripciones-list/suscripciones-list.component';
import { SuscripcionesFormComponent } from './components/suscripciones/suscripciones-form/suscripciones-form.component';
import { PlanesListComponent } from './components/planes/planes-list/planes-list.component';

export const routes: Routes = [
  { path: '', redirectTo: '/socios', pathMatch: 'full' },
  { path: 'socios', component: SociosListComponent },
  { path: 'socios/nuevo', component: SociosFormComponent },
  { path: 'socios/editar/:rut', component: SociosFormComponent },
  { path: 'suscripciones', component: SuscripcionesListComponent },
  { path: 'suscripciones/nuevo', component: SuscripcionesFormComponent },
  { path: 'suscripciones/editar/:id', component: SuscripcionesFormComponent },
  { path: 'planes', component: PlanesListComponent }
];

