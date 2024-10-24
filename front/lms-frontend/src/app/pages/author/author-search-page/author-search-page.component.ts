import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Author } from '../../../features/entities/author/author';
import { AuthorTableViewComponent } from '../../../features/entities/author/author-table-view/author-table-view.component';
import { AuthorSearchFormComponent } from '../../../features/entities/author/author-search-form/author-search-form.component';
import { AuthorStructure } from '../../../features/entities/author/author-structure';

@Component({
	selector: 'app-author-search-page',
	standalone: true,
	imports: [
		AuthorTableViewComponent,
		AuthorSearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './author-search-page.component.html',
	styleUrl: './author-search-page.component.css'
})

export class AuthorSearchPageComponent {
	structure: AuthorStructure = AuthorStructure.instance;
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
