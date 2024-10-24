import {Injectable} from '@angular/core';
import {SearchDTO} from "../entity-utils/search-dto";

@Injectable({
  providedIn: 'root'
})
export class QueryService {

  constructor() {
  }

  public getFromStorage(name: string): SearchDTO | null {
    const dto = localStorage.getItem(name);
    if(!dto) return null;
    const searchDTO = JSON.parse(dto);
    if(searchDTO.pageSize > 100){
      searchDTO.pageSize = 100;
    }
    return searchDTO;
  }

  public saveToStorage(name: string, dto: SearchDTO): void {
    localStorage.setItem(name, JSON.stringify(dto));
  }
  
  public deleteFromStorage(name: string){
    localStorage.removeItem(name);
  }
}
