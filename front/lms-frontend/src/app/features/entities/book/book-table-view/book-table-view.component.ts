import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-book-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './book-table-view.component.html',
	styleUrl: './book-table-view.component.css'
})

export class BookTableViewComponent {
	@Input() structure?: Structure;
}
