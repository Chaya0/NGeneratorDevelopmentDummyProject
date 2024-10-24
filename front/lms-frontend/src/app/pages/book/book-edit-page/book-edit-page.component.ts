import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { BookUpdateFormComponent } from '../../../features/entities/book/book-update-form/book-update-form.component';
import { BookStructure } from '../../../features/entities/book/book-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-book-edit-page',
	standalone: true,
	imports: [BookUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './book-edit-page.component.html',
	styleUrl: './book-edit-page.component.css'
})

export class BookEditPageComponent implements OnInit {
	structure: BookStructure = BookStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
