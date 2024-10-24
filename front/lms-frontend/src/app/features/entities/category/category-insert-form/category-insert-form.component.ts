import { Component } from '@angular/core';
import { GenericInsertFormComponent } from '../../../../core/components/generic-insert-form/generic-insert-form.component';

@Component({
	selector: 'app-category-insert-form',
	standalone: true,
	imports: [GenericInsertFormComponent],
	templateUrl: './category-insert-form.component.html',
	styleUrl: './category-insert-form.component.css'
})

export class CategoryInsertFormComponent extends GenericInsertFormComponent {
}
