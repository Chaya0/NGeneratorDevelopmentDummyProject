import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-fine-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './fine-insert-form.component.html',
	styleUrl: './fine-insert-form.component.css'
})

export class FineInsertFormComponent extends GenericInsertFormComponent {
}
