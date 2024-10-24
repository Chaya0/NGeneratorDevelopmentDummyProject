import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-library-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './library-table-view.component.html',
	styleUrl: './library-table-view.component.css'
})

export class LibraryTableViewComponent {
	@Input() structure?: Structure;
}
