import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-library-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './library-insert-form.component.html',
	styleUrl: './library-insert-form.component.css'
})

export class LibraryInsertFormComponent extends GenericInsertFormComponent {
}
