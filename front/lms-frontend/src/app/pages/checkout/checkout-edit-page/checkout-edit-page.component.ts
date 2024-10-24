import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { CheckoutUpdateFormComponent } from '../../../features/entities/checkout/checkout-update-form/checkout-update-form.component';
import { CheckoutStructure } from '../../../features/entities/checkout/checkout-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-checkout-edit-page',
	standalone: true,
	imports: [CheckoutUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './checkout-edit-page.component.html',
	styleUrl: './checkout-edit-page.component.css'
})

export class CheckoutEditPageComponent implements OnInit {
	structure: CheckoutStructure = CheckoutStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
