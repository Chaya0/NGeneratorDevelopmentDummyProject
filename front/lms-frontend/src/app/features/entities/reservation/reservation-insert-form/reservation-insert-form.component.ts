import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-reservation-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './reservation-insert-form.component.html',
	styleUrl: './reservation-insert-form.component.css'
})

export class ReservationInsertFormComponent extends GenericInsertFormComponent {
}
