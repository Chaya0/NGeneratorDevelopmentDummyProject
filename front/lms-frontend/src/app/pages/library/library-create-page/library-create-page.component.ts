import { Component } from '@angular/core';
import { LibraryInsertFormComponent } from '../../../features/entities/library/library-insert-form/library-insert-form.component';
import { LibraryStructure } from '../../../features/entities/library/library-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-library-create-page',
	standalone: true,
	imports: [LibraryInsertFormComponent, BreadcrumbComponent],
	templateUrl: './library-create-page.component.html',
	styleUrl: './library-create-page.component.css'
})

export class LibraryCreatePageComponent {
	structure: LibraryStructure = LibraryStructure.instance;
}
