import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-checkout-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './checkout-update-form.component.html',
	styleUrl: './checkout-update-form.component.css'
})

export class CheckoutUpdateFormComponent extends GenericUpdateFormComponent {
}
