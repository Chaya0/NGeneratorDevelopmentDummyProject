import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { PublisherUpdateFormComponent } from '../../../features/entities/publisher/publisher-update-form/publisher-update-form.component';
import { PublisherStructure } from '../../../features/entities/publisher/publisher-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-publisher-edit-page',
	standalone: true,
	imports: [PublisherUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './publisher-edit-page.component.html',
	styleUrl: './publisher-edit-page.component.css'
})

export class PublisherEditPageComponent implements OnInit {
	structure: PublisherStructure = PublisherStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
