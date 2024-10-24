import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Category } from '../../../features/entities/category/category';
import { CategoryTableViewComponent } from '../../../features/entities/category/category-table-view/category-table-view.component';
import { CategorySearchFormComponent } from '../../../features/entities/category/category-search-form/category-search-form.component';
import { CategoryStructure } from '../../../features/entities/category/category-structure';

@Component({
	selector: 'app-category-search-page',
	standalone: true,
	imports: [
		CategoryTableViewComponent,
		CategorySearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './category-search-page.component.html',
	styleUrl: './category-search-page.component.css'
})

export class CategorySearchPageComponent {
	structure: CategoryStructure = CategoryStructure.instance;
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
