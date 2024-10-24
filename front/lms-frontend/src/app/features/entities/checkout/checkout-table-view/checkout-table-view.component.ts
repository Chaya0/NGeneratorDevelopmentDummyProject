import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-checkout-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './checkout-table-view.component.html',
	styleUrl: './checkout-table-view.component.css'
})

export class CheckoutTableViewComponent {
	@Input() structure?: Structure;
}
