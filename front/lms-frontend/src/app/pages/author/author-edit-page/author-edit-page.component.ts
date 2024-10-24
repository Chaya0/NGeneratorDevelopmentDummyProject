import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { AuthorUpdateFormComponent } from '../../../features/entities/author/author-update-form/author-update-form.component';
import { AuthorStructure } from '../../../features/entities/author/author-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-author-edit-page',
	standalone: true,
	imports: [AuthorUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './author-edit-page.component.html',
	styleUrl: './author-edit-page.component.css'
})

export class AuthorEditPageComponent implements OnInit {
	structure: AuthorStructure = AuthorStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
