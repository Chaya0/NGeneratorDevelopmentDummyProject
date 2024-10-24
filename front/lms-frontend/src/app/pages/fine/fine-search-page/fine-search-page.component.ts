import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Fine } from '../../../features/entities/fine/fine';
import { FineTableViewComponent } from '../../../features/entities/fine/fine-table-view/fine-table-view.component';
import { FineSearchFormComponent } from '../../../features/entities/fine/fine-search-form/fine-search-form.component';
import { FineStructure } from '../../../features/entities/fine/fine-structure';

@Component({
	selector: 'app-fine-search-page',
	standalone: true,
	imports: [
		FineTableViewComponent,
		FineSearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './fine-search-page.component.html',
	styleUrl: './fine-search-page.component.css'
})

export class FineSearchPageComponent {
	structure: FineStructure = FineStructure.instance;
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
