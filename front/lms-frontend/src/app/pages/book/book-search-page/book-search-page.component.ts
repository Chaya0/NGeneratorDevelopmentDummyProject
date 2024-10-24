import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Book } from '../../../features/entities/book/book';
import { BookTableViewComponent } from '../../../features/entities/book/book-table-view/book-table-view.component';
import { BookSearchFormComponent } from '../../../features/entities/book/book-search-form/book-search-form.component';
import { BookStructure } from '../../../features/entities/book/book-structure';

@Component({
	selector: 'app-book-search-page',
	standalone: true,
	imports: [
		BookTableViewComponent,
		BookSearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './book-search-page.component.html',
	styleUrl: './book-search-page.component.css'
})

export class BookSearchPageComponent {
	structure: BookStructure = BookStructure.instance;
	searchService: SearchService = inject(SearchService);
	translationService = inject(TranslationService);
	items: MenuItem[] = [
		{
			label: this.translationService.translate('new'),
			icon: 'pi pi-plus',
			routerLink: 'create',
			command: () => {}
		}
	];
}
