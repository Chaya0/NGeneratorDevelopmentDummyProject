import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { ReservationUpdateFormComponent } from '../../../features/entities/reservation/reservation-update-form/reservation-update-form.component';
import { ReservationStructure } from '../../../features/entities/reservation/reservation-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-reservation-edit-page',
	standalone: true,
	imports: [ReservationUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './reservation-edit-page.component.html',
	styleUrl: './reservation-edit-page.component.css'
})

export class ReservationEditPageComponent implements OnInit {
	structure: ReservationStructure = ReservationStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
