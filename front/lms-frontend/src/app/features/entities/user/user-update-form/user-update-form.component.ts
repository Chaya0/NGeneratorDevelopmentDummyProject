import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-user-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './user-update-form.component.html',
	styleUrl: './user-update-form.component.css'
})

export class UserUpdateFormComponent extends GenericUpdateFormComponent {
}
