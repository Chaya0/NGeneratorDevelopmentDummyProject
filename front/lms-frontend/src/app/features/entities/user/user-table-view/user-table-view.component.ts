import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-user-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './user-table-view.component.html',
	styleUrl: './user-table-view.component.css'
})

export class UserTableViewComponent {
	@Input() structure?: Structure;
}
