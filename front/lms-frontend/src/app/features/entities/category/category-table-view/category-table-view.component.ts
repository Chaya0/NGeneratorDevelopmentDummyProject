import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-category-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './category-table-view.component.html',
	styleUrl: './category-table-view.component.css'
})

export class CategoryTableViewComponent {
	@Input() structure?: Structure;
}
