import {Component, inject, OnInit} from '@angular/core';
import {NavigationEnd, Route, Router, RouterLink} from "@angular/router";
import {NgClass, NgForOf, NgIf} from "@angular/common";
import {TranslatePipe} from "../../../core/pipes/translate.pipe";
import {routes} from "../../../app.routes";
import {filter, map} from "rxjs/operators";
import {BreadcrumbModule} from "primeng/breadcrumb";
import {MenuItem} from "primeng/api";

interface Breadcrumb {
  label: string;
  url: string;
}

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [
    RouterLink,
    NgForOf,
    NgIf,
    NgClass,
    TranslatePipe,
    BreadcrumbModule
  ],
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.scss'
})
export class BreadcrumbComponent implements OnInit {
  breadcrumbs: Breadcrumb[] = [];
  breadcrumbItems: MenuItem[] = [];
  home: MenuItem = {
    icon: 'pi pi-home',
    routerLink: '/',
  }

  private toMenuItems(breadcrumbs: Breadcrumb[]): MenuItem[] {
    return breadcrumbs.map(e => {
      return {
        label: e.label,
        routerLink: e.url,
        command: () => this.router.navigateByUrl(e.url)
      }
    });
  }

  isLast(item: MenuItem): boolean {
    const index = this.breadcrumbItems.findIndex(breadcrumb => breadcrumb.label === item.label && breadcrumb.url === item.url);
    return index === this.breadcrumbItems.length - 1;
  }

  routes: Route[] = routes;
  protected router: Router = inject(Router);

  ngOnInit(): void {
    // initial load, build breadcrumbs manually
    this.breadcrumbs = this.buildBreadcrumbs();
    this.breadcrumbItems = this.toMenuItems(this.breadcrumbs);

    // subscribe to events to build breadcrumbs automatically
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.buildBreadcrumbs())
    ).subscribe(breadcrumbs => {
      this.breadcrumbs = breadcrumbs;
      this.breadcrumbItems = this.toMenuItems(this.breadcrumbs);
    });
  }

  buildBreadcrumbs(): Breadcrumb[] {
    const currentRoute: Route | undefined = this.findRoute(this.router.url.slice(1));
    if (!currentRoute || !currentRoute.data) return [];
    let breadcrumbs = this.fillBreadcrumbs(currentRoute, []);
    breadcrumbs.reverse().push({label: currentRoute.data['breadcrumb'], url: currentRoute.path || ""})
    return breadcrumbs;
  }

  fillBreadcrumbs(current: Route, breadcrumbs: Breadcrumb[]): Breadcrumb[] {
    if (!current.data || !current.data['parent'] || current.data['parent'] === "") {
      return breadcrumbs;
    }
    const parent: Route | undefined = this.findRoute(current.data['parent']) || {};
    if (parent && parent.data)
      breadcrumbs.push({label: parent.data['breadcrumb'], url: parent.path || ""});
    return this.fillBreadcrumbs(parent, breadcrumbs);
  }

  findRoute(url: string): Route | undefined {
    // First, try to find a matching route with parameters
    let route = this.routes.find(route => this.matchRoute(route.path || '', url));

    // If no specific match is found, try to match the wildcard route
    if (!route) {
      route = this.routes.find(route => route.path === '**');
    }

    return route;
  }

  matchRoute(pattern: string, url: string): boolean {
    const regexPattern = pattern.replace(/:[^\s/]+/g, '([^\\s/]+)').replace(/\*\*/g, '.*');
    const regex = new RegExp(`^${regexPattern}$`);
    return regex.test(url);
  }
}
