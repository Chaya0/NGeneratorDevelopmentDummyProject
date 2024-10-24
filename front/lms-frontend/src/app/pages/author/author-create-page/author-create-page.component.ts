import { Component } from '@angular/core';
import { AuthorInsertFormComponent } from '../../../features/entities/author/author-insert-form/author-insert-form.component';
import { AuthorStructure } from '../../../features/entities/author/author-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-author-create-page',
	standalone: true,
	imports: [AuthorInsertFormComponent, BreadcrumbComponent],
	templateUrl: './author-create-page.component.html',
	styleUrl: './author-create-page.component.css'
})

export class AuthorCreatePageComponent {
	structure: AuthorStructure = AuthorStructure.instance;
}
