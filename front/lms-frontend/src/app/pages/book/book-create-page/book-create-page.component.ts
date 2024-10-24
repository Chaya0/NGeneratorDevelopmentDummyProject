import { Component } from '@angular/core';
import { BookInsertFormComponent } from '../../../features/entities/book/book-insert-form/book-insert-form.component';
import { BookStructure } from '../../../features/entities/book/book-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-book-create-page',
	standalone: true,
	imports: [BookInsertFormComponent, BreadcrumbComponent],
	templateUrl: './book-create-page.component.html',
	styleUrl: './book-create-page.component.css'
})

export class BookCreatePageComponent {
	structure: BookStructure = BookStructure.instance;
}
