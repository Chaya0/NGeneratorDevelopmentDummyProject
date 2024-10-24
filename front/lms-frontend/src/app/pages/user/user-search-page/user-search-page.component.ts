import { Component, inject } from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";
import { NgIf } from "@angular/common";
import {MenuItem} from "primeng/api";
import {PrimeModule} from '../../../shared/prime/prime.modules';
import { BreadcrumbComponent } from '../../../shared/components/breadcrumb/breadcrumb.component';
import { SearchService } from "../../../core/services/search.service";
import { TranslationService } from "../../../core/services/translation.service";
import { User } from '../../../features/entities/user/user';
import { UserTableViewComponent } from '../../../features/entities/user/user-table-view/user-table-view.component';
import { UserSearchFormComponent } from '../../../features/entities/user/user-search-form/user-search-form.component';
import { UserStructure } from '../../../features/entities/user/user-structure';

@Component({
	selector: 'app-user-search-page',
	standalone: true,
	imports: [
		UserTableViewComponent,
		UserSearchFormComponent,
		BreadcrumbComponent,
		PrimeModule,
		RouterLink,
		RouterOutlet,
		NgIf
	],
	templateUrl: './user-search-page.component.html',
	styleUrl: './user-search-page.component.css'
})

export class UserSearchPageComponent {
	structure: UserStructure = UserStructure.instance;
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
