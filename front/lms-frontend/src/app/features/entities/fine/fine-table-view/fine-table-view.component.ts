import { Component, Input } from '@angular/core';
import { GenericTableViewComponent } from '../../../../core/components/generic-table-view/generic-table-view.component';
import { Structure } from "../../structure";

@Component({
	selector: 'app-fine-table-view',
	standalone: true,
	imports: [GenericTableViewComponent],
	templateUrl: './fine-table-view.component.html',
	styleUrl: './fine-table-view.component.css'
})

export class FineTableViewComponent {
	@Input() structure?: Structure;
}
