import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-reservation-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './reservation-table-view.component.html',
	styleUrl: './reservation-table-view.component.css'
})

export class ReservationTableViewComponent {
	@Input() structure?: Structure;
}
