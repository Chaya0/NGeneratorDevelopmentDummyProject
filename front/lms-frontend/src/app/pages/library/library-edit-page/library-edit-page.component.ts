import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { LibraryUpdateFormComponent } from '../../../features/entities/library/library-update-form/library-update-form.component';
import { LibraryStructure } from '../../../features/entities/library/library-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-library-edit-page',
	standalone: true,
	imports: [LibraryUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './library-edit-page.component.html',
	styleUrl: './library-edit-page.component.css'
})

export class LibraryEditPageComponent implements OnInit {
	structure: LibraryStructure = LibraryStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
