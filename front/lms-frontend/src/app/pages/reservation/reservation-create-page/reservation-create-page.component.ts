import { Component } from '@angular/core';
import { ReservationInsertFormComponent } from '../../../features/entities/reservation/reservation-insert-form/reservation-insert-form.component';
import { ReservationStructure } from '../../../features/entities/reservation/reservation-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-reservation-create-page',
	standalone: true,
	imports: [ReservationInsertFormComponent, BreadcrumbComponent],
	templateUrl: './reservation-create-page.component.html',
	styleUrl: './reservation-create-page.component.css'
})

export class ReservationCreatePageComponent {
	structure: ReservationStructure = ReservationStructure.instance;
}
