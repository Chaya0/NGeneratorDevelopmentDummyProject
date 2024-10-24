import { Component } from '@angular/core';
import { GenericUpdateFormComponent } from '../../../../core/components/generic-update-form/generic-update-form.component';

@Component({
	selector: 'app-category-update-form',
	standalone: true,
	imports: [GenericUpdateFormComponent],
	templateUrl: './category-update-form.component.html',
	styleUrl: './category-update-form.component.css'
})

export class CategoryUpdateFormComponent extends GenericUpdateFormComponent {
}
