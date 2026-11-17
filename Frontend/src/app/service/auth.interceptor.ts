import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = localStorage.getItem('auth_token');

    // Headers base que se añaden a todas las peticiones
    const headersToAdd: Record<string, string> = {
      // Salta la página de aviso HTML intermedia de ngrok ("ngrok free tier warning")
      'ngrok-skip-browser-warning': 'true'
    };

    // Si existe JWT, lo adjuntamos en el header Authorization
    if (token) {
      headersToAdd['Authorization'] = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
    }

    // Clonamos la petición con los nuevos encabezados
    request = request.clone({
      setHeaders: headersToAdd
    });

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        // Si el token expira (60 min) o es rechazado, cerramos sesión
        if (error.status === 401 || error.status === 403) {
          localStorage.removeItem('auth_token');
          localStorage.removeItem('empleado_user');
          this.router.navigateByUrl('/login-page');
        }
        return throwError(() => error);
      })
    );
  }
}
