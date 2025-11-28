import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark gym-navbar">
      <div class="container-fluid">
        <a class="navbar-brand gym-brand" routerLink="/">
          <span class="brand-icon">🏋️</span>
          <span class="brand-text">GROSS GYM</span>
          <span class="brand-subtitle">FITNESS</span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <a class="nav-link" routerLink="/socios" routerLinkActive="active">
                <i class="bi bi-people-fill"></i> Socios
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/suscripciones" routerLinkActive="active">
                <i class="bi bi-card-checklist"></i> Suscripciones
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" routerLink="/planes" routerLinkActive="active">
                <i class="bi bi-trophy-fill"></i> Planes
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <div class="gym-hero-banner">
      <div class="container-fluid">
        <h1 class="hero-title">Sistema de Gestión</h1>
      </div>
    </div>

    <div class="container-fluid mt-4 gym-content">
      <router-outlet></router-outlet>
    </div>

    <footer class="gym-footer">
      <div class="container-fluid text-center">
        <p>&copy; 2024 Gross Gym Fitness. Todos los derechos reservados.</p>
        <p class="footer-links">
          <a href="https://grossgym.cl" target="_blank">grossgym.cl</a>
        </p>
      </div>
    </footer>
  `,
  styles: [`
    .gym-navbar {
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
      padding: 12px 0;
      background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%) !important;
    }

    .gym-brand {
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 1.4rem;
    }

    .brand-icon {
      font-size: 2rem;
    }

    .brand-text {
      font-weight: 900;
      color: #ff6b35;
      letter-spacing: 1.5px;
    }

    .brand-subtitle {
      font-weight: 300;
      color: #fff;
      font-size: 0.9rem;
      letter-spacing: 2px;
    }

    .gym-hero-banner {
      background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 50%, #1a1a1a 100%);
      padding: 30px 0;
      margin-bottom: 30px;
      border-bottom: 4px solid #ff6b35;
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
    }

    .hero-title {
      color: white;
      font-size: 2rem;
      font-weight: 800;
      margin: 0;
      text-transform: uppercase;
      letter-spacing: 2px;
      text-align: center;
      text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
    }

    .gym-content {
      min-height: calc(100vh - 400px);
    }

    .gym-footer {
      background: linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%);
      color: rgba(255, 255, 255, 0.8);
      padding: 30px 0;
      margin-top: 50px;
      border-top: 3px solid #ff6b35;
    }

    .gym-footer p {
      margin: 8px 0;
      font-size: 0.9rem;
    }

    .footer-links a {
      color: #ff6b35;
      text-decoration: none;
      font-weight: 600;
      transition: color 0.3s ease;
    }

    .footer-links a:hover {
      color: #ff5722;
      text-decoration: underline;
    }
  `]
})
export class AppComponent {
  title = 'Gross Gym Fitness';
}

