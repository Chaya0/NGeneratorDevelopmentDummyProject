import { Component } from '@angular/core';
import { CheckoutInsertFormComponent } from '../../../features/entities/checkout/checkout-insert-form/checkout-insert-form.component';
import { CheckoutStructure } from '../../../features/entities/checkout/checkout-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-checkout-create-page',
	standalone: true,
	imports: [CheckoutInsertFormComponent, BreadcrumbComponent],
	templateUrl: './checkout-create-page.component.html',
	styleUrl: './checkout-create-page.component.css'
})

export class CheckoutCreatePageComponent {
	structure: CheckoutStructure = CheckoutStructure.instance;
}
