import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-library-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './library-update-form.component.html',
	styleUrl: './library-update-form.component.css'
})

export class LibraryUpdateFormComponent extends GenericUpdateFormComponent {
}
