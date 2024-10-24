import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { FineUpdateFormComponent } from '../../../features/entities/fine/fine-update-form/fine-update-form.component';
import { FineStructure } from '../../../features/entities/fine/fine-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-fine-edit-page',
	standalone: true,
	imports: [FineUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './fine-edit-page.component.html',
	styleUrl: './fine-edit-page.component.css'
})

export class FineEditPageComponent implements OnInit {
	structure: FineStructure = FineStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
