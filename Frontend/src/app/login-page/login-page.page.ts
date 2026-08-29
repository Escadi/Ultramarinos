import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.page.html',
  styleUrls: ['./login-page.page.scss'],
  standalone: false
})
export class LoginPagePage implements OnInit {

  // ─── Campos ──────────────────────────────────────────────────────────────
  email: string = '';
  password: string = '';

  // ─── UI state ────────────────────────────────────────────────────────────
  showPassword = false;
  emailFocused = false;
  passFocused  = false;
  isLoading    = false;
  loginError   = false;
  errorMessage = 'Correo o contraseña incorrectos. Inténtalo de nuevo.';
  anio = new Date().getFullYear();

  constructor(private router: Router) {}

  ngOnInit() {}

  async getlogin() {
    this.loginError = false;

    if (!this.email.trim() || !this.password.trim()) {
      this.loginError  = true;
      this.errorMessage = 'Por favor, completa todos los campos.';
      return;
    }

    this.isLoading = true;

    // Simulación de llamada async (sustituir por servicio real)
    await new Promise(r => setTimeout(r, 800));

    this.isLoading = false;
    this.router.navigateByUrl('/dashboard-control');
  }
}
