import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-publisher-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './publisher-table-view.component.html',
	styleUrl: './publisher-table-view.component.css'
})

export class PublisherTableViewComponent {
	@Input() structure?: Structure;
}
