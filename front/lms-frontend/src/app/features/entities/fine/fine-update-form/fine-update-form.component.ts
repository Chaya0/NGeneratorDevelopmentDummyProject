import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-fine-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './fine-update-form.component.html',
	styleUrl: './fine-update-form.component.css'
})

export class FineUpdateFormComponent extends GenericUpdateFormComponent {
}
