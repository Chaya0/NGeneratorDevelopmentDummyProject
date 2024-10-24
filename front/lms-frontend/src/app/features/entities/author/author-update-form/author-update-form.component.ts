import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-author-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './author-update-form.component.html',
	styleUrl: './author-update-form.component.css'
})

export class AuthorUpdateFormComponent extends GenericUpdateFormComponent {
}
