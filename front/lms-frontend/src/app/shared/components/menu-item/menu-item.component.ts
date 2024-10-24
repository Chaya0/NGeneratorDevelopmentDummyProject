import {ChangeDetectorRef, Component, HostBinding, inject, Input, OnDestroy, OnInit} from '@angular/core';

import {NavigationEnd, Route, Router, RouterModule} from '@angular/router';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {CommonModule} from '@angular/common';
import {filter, Subscription} from 'rxjs';
import {MenuService} from '../menu-wrapper/menu.service';
import {PrimeModule} from '../../prime/prime.modules';
import {MenuItem} from 'primeng/api';
import {PermissionService} from '../../../core/services/permission.service';

@Component({
  selector: 'app-menu-item',
  standalone: true,
  imports: [PrimeModule, RouterModule, CommonModule],
  templateUrl: './menu-item.component.html',
  styleUrl: './menu-item.component.scss',
  animations: [
    trigger('children', [
      state('collapsed', style({
        height: '0'
      })),
      state('expanded', style({
        height: '*'
      })),
      transition('collapsed <=> expanded', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)'))
    ])
  ]
})
export class MenuItemComponent implements OnInit, OnDestroy {

  @Input() item: any;

  @Input() index!: number;

  @Input() @HostBinding('class.layout-root-menuitem') root!: boolean;

  @Input() parentKey!: string;

  active = false;

  menuSourceSubscription: Subscription;

  menuResetSubscription: Subscription;

  key: string = "";
  permissionService = inject(PermissionService);

  constructor(private cd: ChangeDetectorRef, public router: Router, private menuService: MenuService) {
    this.menuSourceSubscription = this.menuService.menuSource$.subscribe(value => {
      Promise.resolve(null).then(() => {
        if (value.routeEvent) {
          this.active = (value.key === this.key || value.key.startsWith(this.key + '-'));
        } else if (value.key !== this.key && !value.key.startsWith(this.key + '-')) {
          this.active = false;
        }
      });
    });

    this.menuResetSubscription = this.menuService.resetSource$.subscribe(() => {
      this.active = false;
    });

    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        if (this.item.routerLink) {
          this.updateActiveStateFromRoute();
        }
      });
  }

  get submenuAnimation() {
    return this.root ? 'expanded' : (this.active ? 'expanded' : 'collapsed');
  }

  @HostBinding('class.active-menuitem')
  get activeClass() {
    return this.active && !this.root;
  }

  ngOnInit() {
    this.key = this.parentKey ? this.parentKey + '-' + this.index : String(this.index);
    if (this.item.routerLink) {
      this.updateActiveStateFromRoute();
    }
  }

  updateActiveStateFromRoute() {
    let activeRoute = this.router.isActive(this.item.routerLink[0], {
      paths: 'exact',
      queryParams: 'ignored',
      matrixParams: 'ignored',
      fragment: 'ignored'
    });

    if (activeRoute) {
      this.menuService.onMenuStateChange({key: this.key, routeEvent: true});
    }
  }

  itemClick(event: Event) {
    // avoid processing disabled items
    if (this.item.disabled) {
      event.preventDefault();
      return;
    }

    // execute command
    if (this.item.command) {
      this.item.command({originalEvent: event, item: this.item});
    }

    // toggle active state
    if (this.item.items) {
      this.active = !this.active;
    }

    this.menuService.onMenuStateChange({key: this.key});
  }

  ngOnDestroy() {
    if (this.menuSourceSubscription) {
      this.menuSourceSubscription.unsubscribe();
    }

    if (this.menuResetSubscription) {
      this.menuResetSubscription.unsubscribe();
    }
  }

  hasPermission(menuItem: MenuItem): boolean {
    if (menuItem.routerLink) {
      let menuRoute: Route | undefined;
      if (menuItem.routerLink[0].startsWith('/'))
        menuRoute = this.getRouteByPath(menuItem.routerLink[0].slice(1))
      else {
        menuRoute = this.getRouteByPath(menuItem.routerLink[0])
      }
      if (menuRoute) {
        return this.permissionService.hasPermissionForRoute(menuRoute)
      }
      return false;
    }
    if(menuItem.items){
      for(const item of menuItem.items){
        let menuRoute: Route | undefined;
        if (item.routerLink[0].startsWith('/'))
          menuRoute = this.getRouteByPath(item.routerLink[0].slice(1))
        else {
          menuRoute = this.getRouteByPath(item.routerLink[0])
        }
        if (menuRoute) {
          if(this.permissionService.hasPermissionForRoute(menuRoute)){
            return true;
          }
        }
      }
      return false;
    }
    return true
  }

  getRouteByPath(path: string): Route | undefined {
    return this.findRouteByPath(this.router.config, path);
  }

  private findRouteByPath(routes: Route[], path: string, parentPath: string = ''): Route | undefined {
    for (const route of routes) {
      const fullPath = parentPath + '/' + route.path;

      // Remove leading slashes for consistent path matching
      const normalizedFullPath = fullPath.replace(/\/+/g, '/').replace(/^\//, '');

      if (normalizedFullPath === path) {
        return route;
      }

      if (route.children) {
        const foundRoute = this.findRouteByPath(route.children, path, fullPath);
        if (foundRoute) {
          return foundRoute;
        }
      }
    }

    return undefined;
  }
}
