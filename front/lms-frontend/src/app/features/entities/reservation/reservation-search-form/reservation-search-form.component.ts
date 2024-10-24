import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { ReservationStructure } from '../reservation-structure';

@Component({
	selector: 'app-reservation-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './reservation-search-form.component.html',
	styleUrl: './reservation-search-form.component.css'
})

export class ReservationSearchFormComponent {
	@Input() structure!: ReservationStructure;
}
