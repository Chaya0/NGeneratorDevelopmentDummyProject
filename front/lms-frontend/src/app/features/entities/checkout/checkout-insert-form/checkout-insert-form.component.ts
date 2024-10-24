import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-checkout-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './checkout-insert-form.component.html',
	styleUrl: './checkout-insert-form.component.css'
})

export class CheckoutInsertFormComponent extends GenericInsertFormComponent {
}
