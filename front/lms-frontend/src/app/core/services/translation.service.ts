import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import { PrimeNGConfig } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class TranslationService {
  private translations: any = {};
  private translationSubject = new BehaviorSubject<any>(this.translations); 

  private http = inject(HttpClient);
  protected primengConfig = inject(PrimeNGConfig);

  loadTranslations(lang: string): Observable<any> {
    return this.http.get(`/assets/i18n/${lang}.json`).pipe(
      map((translations: any) => {
        this.translations = translations;
        this.translationSubject.next(this.translations); 
        return translations;
      }),
      catchError(() => of({}))
    );
  }

  translate(key: string, replace: string = ""): string {
    const keys = key.split('.');
    let result = this.translations;
    for (const k of keys) {
      result = result ? result[k] : key;
    }
    return result?.replace("${}", replace) || key;
  }

  async setLanguage(lang: string) {
    this.loadTranslations(lang).subscribe(locale => {
      this.loadPrimeNgCalendarLocale(locale);
    });
  }
  
  private loadPrimeNgCalendarLocale(locale: any) {
    this.primengConfig.setTranslation(locale);
  }

  getTranslationChanges(): Observable<any> {
    return this.translationSubject.asObservable(); // Expose translation changes as Observable
  }
}
