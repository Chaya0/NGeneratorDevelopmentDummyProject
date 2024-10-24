import {inject, Injectable} from '@angular/core';
import {TranslationService} from './translation.service';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {BehaviorSubject, Observable} from "rxjs";
export interface Language {
  code: string;
  shortName: string;
  fullName: string;
  flag: string;
}

@Injectable({
  providedIn: 'root'
})
export class LanguageService {
  static readonly languages: Language[] = [
    {code: 'en', shortName: 'EN', fullName: 'English', flag: 'fi fi-gb'}
  ];
  translationService = inject(TranslationService);
  protected http: HttpClient = inject(HttpClient);
  private defaultLanguage = environment.defaultLanguage;

  getLanguages(): Language[] {
    return LanguageService.languages;
  }

  changeLanguage(languageCode: string) {
    this.setLanguage(languageCode);
  }

	//Add your logic of loading languge for user
  loadLanguage() {
    this.setLanguage(this.defaultLanguage)
  }

  private async setLanguage(language: string) {
    await this.translationService.setLanguage(language);
    let obj = LanguageService.languages.filter(e => e.code === language)[0];
  }

}
