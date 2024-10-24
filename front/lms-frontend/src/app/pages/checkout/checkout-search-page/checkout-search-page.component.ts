import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { Checkout } from '../../../features/entities/checkout/checkout';
import { CheckoutTableViewComponent } from '../../../features/entities/checkout/checkout-table-view/checkout-table-view.component';
import { CheckoutSearchFormComponent } from '../../../features/entities/checkout/checkout-search-form/checkout-search-form.component';
import { CheckoutStructure } from '../../../features/entities/checkout/checkout-structure';

@Component({
	selector: 'app-checkout-search-page',
	standalone: true,
	imports: [
		CheckoutTableViewComponent,
		CheckoutSearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './checkout-search-page.component.html',
	styleUrl: './checkout-search-page.component.css'
})

export class CheckoutSearchPageComponent {
	structure: CheckoutStructure = CheckoutStructure.instance;
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
