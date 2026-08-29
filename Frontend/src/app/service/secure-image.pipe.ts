import { Pipe, PipeTransform } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Pipe({
  name: 'secureImage',
  standalone: false
})
export class SecureImagePipe implements PipeTransform {
  constructor(private http: HttpClient) { }

  transform(filename: string): Observable<string> {
    if (!filename || filename === 'null' || filename === 'undefined') {
      return of("https://ionicframework.com/docs/img/demos/avatar.svg");
    }

    const url = `${environment.apiUrl}/api/images/${filename}`;

    // Si la URL que viene ya es un base64 o blob local del HTML 
    if (filename.startsWith('blob:') || filename.startsWith('data:')) {
      return of(filename);
    }

    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });

    return this.http.get(url, { headers, responseType: 'blob' }).pipe(
      map(blob => URL.createObjectURL(blob)),
      catchError(() => of("https://ionicframework.com/docs/img/demos/avatar.svg"))
    );
  }
}
