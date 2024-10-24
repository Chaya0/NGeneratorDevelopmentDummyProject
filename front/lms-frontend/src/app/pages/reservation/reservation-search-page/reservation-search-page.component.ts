import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Reservation } from '../../../features/entities/reservation/reservation';
import { ReservationTableViewComponent } from '../../../features/entities/reservation/reservation-table-view/reservation-table-view.component';
import { ReservationSearchFormComponent } from '../../../features/entities/reservation/reservation-search-form/reservation-search-form.component';
import { ReservationStructure } from '../../../features/entities/reservation/reservation-structure';

@Component({
	selector: 'app-reservation-search-page',
	standalone: true,
	imports: [
		ReservationTableViewComponent,
		ReservationSearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './reservation-search-page.component.html',
	styleUrl: './reservation-search-page.component.css'
})

export class ReservationSearchPageComponent {
	structure: ReservationStructure = ReservationStructure.instance;
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
