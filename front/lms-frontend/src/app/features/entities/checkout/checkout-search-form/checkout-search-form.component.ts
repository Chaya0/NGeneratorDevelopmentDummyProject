import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { CheckoutStructure } from '../checkout-structure';

@Component({
	selector: 'app-checkout-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './checkout-search-form.component.html',
	styleUrl: './checkout-search-form.component.css'
})

export class CheckoutSearchFormComponent {
	@Input() structure!: CheckoutStructure;
}
