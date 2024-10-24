import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-book-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './book-insert-form.component.html',
	styleUrl: './book-insert-form.component.css'
})

export class BookInsertFormComponent extends GenericInsertFormComponent {
}
