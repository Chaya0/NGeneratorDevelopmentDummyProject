import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {BehaviorSubject, firstValueFrom, Observable} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {ToastService} from "./toast.service";
import {PermissionService} from './permission.service';
import {User} from "../../features/entities/user/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl + 'auth';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  authEvent$ = this.isAuthenticatedSubject.asObservable();
  sessionValidationResult: boolean | null = null;
  private toastService = inject(ToastService);
  private username!: string;

  constructor(
    private http: HttpClient,
    private router: Router,
    private permissionService: PermissionService
  ) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { "username": username, "password": password }, { withCredentials: true })
      .pipe(
        map((response: any) => {
          this.isAuthenticatedSubject.next(true);
          this.username = response.data.username;
          this.permissionService.setPermissions(response.data.permissions || []);
          return response;
        }),
        catchError((error) => {
          throw error;
        })
      );
  }

  changePassword(user: User): Observable<any> {
    return this.http.post(`${this.apiUrl}/change-password`, user, { withCredentials: true })
      .pipe(
        map((response: any) => {
          this.username = response.data.username;
          this.permissionService.setPermissions(response.data.permissions || []);
          this.isAuthenticatedSubject.next(true);
          return response;
        }),
        catchError((error) => {
          throw error;
        })
      );
  }

  logout(showLogoutMessage: boolean): void {
    this.isAuthenticatedSubject.next(false);
    this.permissionService.clearPermissions();
    this.sessionValidationResult = false;
    this.http.post(`${this.apiUrl}/logout`, {}, { withCredentials: true })
    .pipe(
      map((response: any) => {
        if(showLogoutMessage) this.toastService.showInfo(response.data || 'Logged out successfully!');
        this.router.navigate(['/login']);
        }),
        catchError((error) => {
          console.log(error);
          this.router.navigate(['/login']);
          throw error;
        })
      ).subscribe();
  }

  refreshToken(): Observable<any> {
    return this.http.post(`${this.apiUrl}/refresh-token`, {}, { withCredentials: true })
      .pipe(
        map((response: any) => {
          this.username = response.data.username;
          this.permissionService.setPermissions(response.data.permissions || []);
          this.isAuthenticatedSubject.next(true);
          return response;
        }),
        catchError((error) => {
          throw error;
        })
      );
  }

  async isSessionValid(): Promise<boolean> {
    if (this.sessionValidationResult !== null) {
      return this.sessionValidationResult;
    }
    try {
      let response = await firstValueFrom(this.http.get<any>(`${this.apiUrl}/validate-session`, { withCredentials: true }));
      const isValid = response.code === '200';
      this.isAuthenticatedSubject.next(isValid);
      this.username = response.data.username;
      this.permissionService.setPermissions(response.data.permissions || []);
      this.sessionValidationResult = isValid;
      return isValid;
    } catch (error) {
      // this.toastService.showError("Invalid Session.");
      this.isAuthenticatedSubject.next(false);
      this.permissionService.clearPermissions();
      this.sessionValidationResult = false;
      return false;
    }
  }

  getUsername() {
    return this.username;
  }
}
