import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Library } from '../../../features/entities/library/library';
import { LibraryTableViewComponent } from '../../../features/entities/library/library-table-view/library-table-view.component';
import { LibrarySearchFormComponent } from '../../../features/entities/library/library-search-form/library-search-form.component';
import { LibraryStructure } from '../../../features/entities/library/library-structure';

@Component({
	selector: 'app-library-search-page',
	standalone: true,
	imports: [
		LibraryTableViewComponent,
		LibrarySearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './library-search-page.component.html',
	styleUrl: './library-search-page.component.css'
})

export class LibrarySearchPageComponent {
	structure: LibraryStructure = LibraryStructure.instance;
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
