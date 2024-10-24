import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { CategoryUpdateFormComponent } from '../../../features/entities/category/category-update-form/category-update-form.component';
import { CategoryStructure } from '../../../features/entities/category/category-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-category-edit-page',
	standalone: true,
	imports: [CategoryUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './category-edit-page.component.html',
	styleUrl: './category-edit-page.component.css'
})

export class CategoryEditPageComponent implements OnInit {
	structure: CategoryStructure = CategoryStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
