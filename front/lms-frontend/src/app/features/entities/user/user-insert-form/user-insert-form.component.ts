import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-user-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './user-insert-form.component.html',
	styleUrl: './user-insert-form.component.css'
})

export class UserInsertFormComponent extends GenericInsertFormComponent {
}
