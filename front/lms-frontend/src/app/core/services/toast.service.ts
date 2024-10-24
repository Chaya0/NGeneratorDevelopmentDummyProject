import {inject, Injectable} from '@angular/core';
import {MessageService} from 'primeng/api';
import {TranslationService} from "./translation.service";

@Injectable({
  providedIn: 'root',
})
//TODO da se stavi u nesto sto hvata errore da ispise errore tipa bad request
export class ToastService {
  private messageService = inject(MessageService);
  private translationService = inject(TranslationService)

  showSuccess(detail: string) {
    setTimeout(() => {
      this.messageService.add({
        severity: 'success',
        summary: this.translationService.translate("success"),
        detail: this.translationService.translate(detail)
      });
    }, 500);
  }

  showError(detail: string) {
    setTimeout(() => {
      this.messageService.add({
        severity: 'error',
        summary: this.translationService.translate('error'),
        detail: this.translationService.translate(detail)
      });
    }, 500);
  }

  showInfo(detail: string) {
    setTimeout(() => {
      this.messageService.add({
        severity: 'info',
        summary: this.translationService.translate('info'),
        detail: this.translationService.translate(detail)
      });
    }, 500);
  }

  showWarn(detail: string) {
    setTimeout(() => {
      this.messageService.add({
        severity: 'warn',
        summary: this.translationService.translate('warning'),
        detail: this.translationService.translate(detail)
      });
    }, 500);
  }
}
