import {Component, inject, OnDestroy} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {SearchDTO} from "../../entity-utils/search-dto";
import {Page} from "../../entity-utils/page";
import {ApiService} from "../../services/api.service";
import {AppUtils} from "../../../shared/utils/app-utils";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [],
  template: '',
  styles: []
})
export class RootComponent implements OnDestroy {
  protected subs: Subscription[] = [];
  protected service: ApiService = inject(ApiService);

  ngOnDestroy() {
    this.subs.forEach(sub => {
      sub.unsubscribe();
    });
  }

  search(body: SearchDTO, entity: string, responseFn: Function): void {
    let sub = this.service.search(body, AppUtils.toFirstLetterLowercase(entity)).subscribe((data: Page) => {
      responseFn(data);
    });
    this.subs.push(sub);
  }

  delete(id: any, entity: string, responseFn: Function): void {
    let sub = this.service.delete(AppUtils.toFirstLetterLowercase(entity), id).subscribe((data: any) => {
      responseFn(data);
    });
    this.subs.push(sub);
  }

  insert(body: {}, entity: string, responseFn: Function): void {
    let sub = this.service.insert(AppUtils.toFirstLetterLowercase(entity), body).subscribe((data: {}) => {
      responseFn(data);
    });
    this.subs.push(sub);
  }

  update(body: {}, entity: string, responseFn: Function): void {
    let sub = this.service.update(AppUtils.toFirstLetterLowercase(entity), body).subscribe((data: {}) => {
      responseFn(data);
    });
    this.subs.push(sub);
  }

  getAll(entity: string, responseFn: Function): void {
    let sub = this.service.getAll(AppUtils.toFirstLetterLowercase(entity)).subscribe((data: any[]) => {
      responseFn(data);
    });
    this.subs.push(sub);
  }

  getById(id: any, entity: string, responseFn: Function): void {
    let sub = this.service.getById(AppUtils.toFirstLetterLowercase(entity), id).subscribe((data: any) => {
      responseFn(data);
    });
    this.subs.push(sub);
  }

  performSubscription(sub: Observable<any>, res: any) {
    let s = sub.subscribe((data: any) => {
      res(data);
    })
    this.subs.push(s);
  }
}
