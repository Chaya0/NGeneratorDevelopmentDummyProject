import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-publisher-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './publisher-insert-form.component.html',
	styleUrl: './publisher-insert-form.component.css'
})

export class PublisherInsertFormComponent extends GenericInsertFormComponent {
}
