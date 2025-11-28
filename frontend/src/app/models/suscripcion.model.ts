import { Socio } from './socio.model';
import { Plan } from './plan.model';
import { TipoPago } from './tipo-pago.model';
import { Estado } from './estado.model';

export interface Suscripcion {
  idSuscripcion?: number;
  fechaCreacion: string;
  nroTransaccion: string;
  tipoPago: TipoPago;
  plan: Plan;
  montoPlan?: number;
  montoMatricula?: number;
  nroCuotas?: number;
  fechaTermino?: string;
  estado?: Estado;
  socio: Socio;
}

