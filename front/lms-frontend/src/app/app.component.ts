import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from './core/services/auth.service';
import { TranslationService } from './core/services/translation.service';
import { AsyncPipe, NgIf } from "@angular/common";
import { AppLayoutComponent } from "./shared/components/layout/layout.component";
import { ToastService } from './core/services/toast.service';
import { ToastModule } from 'primeng/toast';
import { LanguageService } from "./core/services/language.service";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  imports: [RouterModule, AsyncPipe, NgIf, AppLayoutComponent, ToastModule],
  providers: []
})
export class AppComponent {
  title = 'angular';
  private authService = inject(AuthService);
  private translationService = inject(TranslationService);
  private languageService = inject(LanguageService);
  router: Router = inject(Router)

  toastService = inject(ToastService)

  isLoggedIn() {
    return this.authService.authEvent$;
  }

  isPageWithMenu() {
    return this.router.url !== '/pageNotFound';
  }

  constructor() {
    this.languageService.loadLanguage();
  }
}
