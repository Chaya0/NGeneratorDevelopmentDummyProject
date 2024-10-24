import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-publisher-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './publisher-update-form.component.html',
	styleUrl: './publisher-update-form.component.css'
})

export class PublisherUpdateFormComponent extends GenericUpdateFormComponent {
}
