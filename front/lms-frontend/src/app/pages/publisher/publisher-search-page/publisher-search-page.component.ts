import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Publisher } from '../../../features/entities/publisher/publisher';
import { PublisherTableViewComponent } from '../../../features/entities/publisher/publisher-table-view/publisher-table-view.component';
import { PublisherSearchFormComponent } from '../../../features/entities/publisher/publisher-search-form/publisher-search-form.component';
import { PublisherStructure } from '../../../features/entities/publisher/publisher-structure';

@Component({
	selector: 'app-publisher-search-page',
	standalone: true,
	imports: [
		PublisherTableViewComponent,
		PublisherSearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './publisher-search-page.component.html',
	styleUrl: './publisher-search-page.component.css'
})

export class PublisherSearchPageComponent {
	structure: PublisherStructure = PublisherStructure.instance;
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
