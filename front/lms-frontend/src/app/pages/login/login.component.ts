import {Component, inject, OnInit, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {AuthService} from "../../core/services/auth.service";
import {Router} from "@angular/router";
import {NgIf, NgOptimizedImage} from "@angular/common";
import {ToastService} from "../../core/services/toast.service";
import {PrimeModule} from "../../shared/prime/prime.modules";
import {PasswordModule} from "primeng/password";
import { TranslationService } from '../../core/services/translation.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [NgIf, NgOptimizedImage, ReactiveFormsModule, PrimeModule, PasswordModule]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  toastService = inject(ToastService)
  translationService = inject(TranslationService)

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.authService.authEvent$.subscribe(data => {
      if (data) this.router.navigate(['']).then();
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const username = this.loginForm.value.username;
      const password = this.loginForm.value.password;
      this.authService.login(username, password).subscribe({
        
        next: () => {
          this.router.navigate(['/'])
        },
        error: err => {
          if(err.error.localizationKey) {
            this.toastService.showError(this.translationService.translate(err.error.localizationKey))
          }else{
            this.toastService.showError(err.message)
          }
          console.error('Login failed', err)
        }
      });
    }
  }

  hide = signal(true);

  togglePassword() {
    this.hide.set(!this.hide());
  }
}
