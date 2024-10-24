import { Component } from '@angular/core';
import { FineInsertFormComponent } from '../../../features/entities/fine/fine-insert-form/fine-insert-form.component';
import { FineStructure } from '../../../features/entities/fine/fine-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-fine-create-page',
	standalone: true,
	imports: [FineInsertFormComponent, BreadcrumbComponent],
	templateUrl: './fine-create-page.component.html',
	styleUrl: './fine-create-page.component.css'
})

export class FineCreatePageComponent {
	structure: FineStructure = FineStructure.instance;
}
