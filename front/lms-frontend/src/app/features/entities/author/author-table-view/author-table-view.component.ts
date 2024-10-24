import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-author-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './author-table-view.component.html',
	styleUrl: './author-table-view.component.css'
})

export class AuthorTableViewComponent {
	@Input() structure?: Structure;
}
