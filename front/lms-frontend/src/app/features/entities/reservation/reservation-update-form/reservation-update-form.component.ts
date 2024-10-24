import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-reservation-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './reservation-update-form.component.html',
	styleUrl: './reservation-update-form.component.css'
})

export class ReservationUpdateFormComponent extends GenericUpdateFormComponent {
}
