import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-book-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './book-update-form.component.html',
	styleUrl: './book-update-form.component.css'
})

export class BookUpdateFormComponent extends GenericUpdateFormComponent {
}
