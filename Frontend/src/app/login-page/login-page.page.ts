import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService, LoginResponseApi } from '../service/api.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.page.html',
  styleUrls: ['./login-page.page.scss'],
  standalone: false
})
export class LoginPagePage implements OnInit {

  // ─── Campos ──────────────────────────────────────────────────────────────
  codigoEmpleado: string = '';
  password: string = '';

  // ─── UI state ────────────────────────────────────────────────────────────
  showPassword = false;
  codigoFocused = false;
  passFocused  = false;
  isLoading    = false;
  loginError   = false;
  errorMessage = 'Código de empleado o contraseña incorrectos. Inténtalo de nuevo.';
  anio = new Date().getFullYear();

  constructor(
    private router: Router,
    private apiService: ApiService
  ) {}

  ngOnInit() {
    // Si ya existe sesión activa, redirigir automáticamente
    const token = localStorage.getItem('auth_token');
    if (token) {
      this.router.navigateByUrl('/dashboard-control');
    }
  }

  getlogin() {
    this.loginError = false;

    if (!this.codigoEmpleado.trim() || !this.password.trim()) {
      this.loginError = true;
      this.errorMessage = 'Por favor, completa todos los campos.';
      return;
    }

    this.isLoading = true;

    this.apiService.login({
      codigoEmpleado: this.codigoEmpleado.trim(),
      password: this.password.trim()
    }).subscribe({
      next: (res: LoginResponseApi) => {
        this.isLoading = false;
        // Guardar token y datos del empleado en localStorage
        localStorage.setItem('auth_token', res.token);
        localStorage.setItem('empleado_user', JSON.stringify(res));

        this.router.navigateByUrl('/dashboard-control');
      },
      error: (err) => {
        this.isLoading = false;
        this.loginError = true;
        if (err.status === 401) {
          this.errorMessage = 'Código de empleado o contraseña incorrectos.';
        } else if (err.status === 403) {
          this.errorMessage = 'El empleado se encuentra inactivo.';
        } else {
          this.errorMessage = 'Error de conexión con el servidor. Revisa que el backend esté activo.';
        }
      }
    });
  }
}
