import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SearchDTO} from "../entity-utils/search-dto";
import {Page} from "../entity-utils/page";
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  protected apiUrl = environment.apiUrl + 'api';
  protected http: HttpClient = inject(HttpClient);

  search(searchDTO: SearchDTO, entity: string): Observable<Page> {
    return this.http.post<Page>(`${this.apiUrl}/${entity}/searchPageable`, searchDTO);
  }

  searchAll<T>(searchDTO: SearchDTO, entity: string): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${entity}/search`, searchDTO);
  }

  getAll<T>(entity: string): Observable<[T]> {
    return this.http.get<[T]>(`${this.apiUrl}/${entity}`);
  }

  getById<T>(entity: string, id: any): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}/${entity}/${id}`);
  }

  insert<T>(entity: string, formData: T): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${entity}`, formData);
  }

  update<T>(entity: string, formData: T): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${entity}`, formData);
  }

  delete<T>(entity: string, id: any): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}/${entity}/${id}`);
  }
}
