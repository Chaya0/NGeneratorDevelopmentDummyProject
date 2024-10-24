import { Component } from '@angular/core';
import { PublisherInsertFormComponent } from '../../../features/entities/publisher/publisher-insert-form/publisher-insert-form.component';
import { PublisherStructure } from '../../../features/entities/publisher/publisher-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-publisher-create-page',
	standalone: true,
	imports: [PublisherInsertFormComponent, BreadcrumbComponent],
	templateUrl: './publisher-create-page.component.html',
	styleUrl: './publisher-create-page.component.css'
})

export class PublisherCreatePageComponent {
	structure: PublisherStructure = PublisherStructure.instance;
}
