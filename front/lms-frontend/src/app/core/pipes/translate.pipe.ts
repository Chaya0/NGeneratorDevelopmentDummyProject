import {inject, Pipe, PipeTransform} from '@angular/core';
import {TranslationService} from '../services/translation.service';

@Pipe({
  name: 'translate',
  standalone: true,
  pure: false
})
export class TranslatePipe implements PipeTransform {
  private translationService = inject(TranslationService);

  transform(value: any): string {
    return this.translationService.translate(value);
  }
}
