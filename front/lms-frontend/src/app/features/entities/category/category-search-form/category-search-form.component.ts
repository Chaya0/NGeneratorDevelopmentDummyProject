import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { CategoryStructure } from '../category-structure';

@Component({
	selector: 'app-category-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './category-search-form.component.html',
	styleUrl: './category-search-form.component.css'
})

export class CategorySearchFormComponent {
	@Input() structure!: CategoryStructure;
}
