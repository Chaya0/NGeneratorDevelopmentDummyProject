import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-author-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './author-insert-form.component.html',
	styleUrl: './author-insert-form.component.css'
})

export class AuthorInsertFormComponent extends GenericInsertFormComponent {
}
