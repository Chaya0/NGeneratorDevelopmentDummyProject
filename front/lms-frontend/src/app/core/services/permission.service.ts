import {Injectable} from '@angular/core';
import {Route} from '@angular/router';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {
  permissions: Array<string> = [];
  private permissionsSubject = new BehaviorSubject<Array<string>>(this.permissions);

  setPermissions(permissions: Array<string>): void {
    this.permissions = permissions;
    this.permissionsSubject.next(permissions);
    this.permissionsLoadedResolve(); // Resolve permissionsLoaded promise on permissions update
  }

  clearPermissions(): void {
    this.permissions = [];
    this.permissionsSubject.next(this.permissions);
  }

  hasPermissionForRoute(route: Route): boolean {
    if (!route.data || !route?.data['permissions']) return true;
    const requiredPermissions = route.data['permissions'] as string[];
    if (!requiredPermissions || requiredPermissions.length === 0) {
      return true;
    }
    return requiredPermissions.every(permission => (this.permissions.includes(permission) || this.permissions.includes('Admin')));
  }

  containsDeletePermission(entityName: string): boolean {
    let permissionName: string = "can_delete_" + this.convertToSnakeCase(entityName);
    return this.permissions.includes(permissionName);
  }

  containsUpdatePermission(entityName: string): boolean {
    let permissionName: string = "can_update_" + this.convertToSnakeCase(entityName);
    return this.permissions.includes(permissionName);
  }

  async waitForPermissions(): Promise<void> {
    while (this.permissions.length === 0) {
      await new Promise(resolve => setTimeout(resolve, 100)); // Check every 100ms
    }
  }

  private permissionsLoadedResolve: (value?: void | PromiseLike<void>) => void = () => {
  };

  private permissionsLoaded = new Promise<void>((resolve) => this.permissionsLoadedResolve = resolve);

  private convertToSnakeCase(input: string): string {
    return input
      .replace(/([a-z])([A-Z])/g, '$1_$2') // Insert underscore between lowercase and uppercase letters
      .replace(/([A-Z])([A-Z][a-z])/g, '$1_$2') // Insert underscore between uppercase letters followed by lowercase letters
      .toLowerCase(); // Convert the whole string to lowercase
  }
}
