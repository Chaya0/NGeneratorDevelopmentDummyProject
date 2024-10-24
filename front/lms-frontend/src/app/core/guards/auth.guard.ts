import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {PermissionService} from '../services/permission.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  isLoggedIn?: boolean;

  constructor(private permissionService: PermissionService, private authService: AuthService, private router: Router) {
  }

  async canActivate(route: ActivatedRouteSnapshot): Promise<boolean> {
    this.authService.authEvent$.subscribe(data => {
      this.isLoggedIn = data;
    });
    if (this.isLoggedIn){
      const requiredPermissions = route.data['permissions'] as string[];
      if(!requiredPermissions) return true;
      if (requiredPermissions && this.permissionService.permissions && requiredPermissions.every(permission => this.permissionService.permissions.includes(permission) || this.permissionService.permissions.includes('Admin'))) {
        return true
      }else{
        await this.router.navigate(['/']);
        return false;
      }
    }
    const isLoggedIn = await this.authService.isSessionValid();
    if (isLoggedIn) {
      const requiredPermissions = route.data['permissions'] as string[];
      if(!requiredPermissions) return true;
      if (requiredPermissions && requiredPermissions.every(permission => this.permissionService.permissions.includes(permission) || this.permissionService.permissions.includes('Admin'))) {
        return true;
      } else {
        await this.router.navigate(['/']);
      }
      return true;
    }
    this.authService.logout(false)
    await this.router.navigate(['/login']);
    return false;
  }
}
