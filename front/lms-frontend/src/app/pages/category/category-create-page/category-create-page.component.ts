import { Component } from '@angular/core';
import { CategoryInsertFormComponent } from '../../../features/entities/category/category-insert-form/category-insert-form.component';
import { CategoryStructure } from '../../../features/entities/category/category-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-category-create-page',
	standalone: true,
	imports: [CategoryInsertFormComponent, BreadcrumbComponent],
	templateUrl: './category-create-page.component.html',
	styleUrl: './category-create-page.component.css'
})

export class CategoryCreatePageComponent {
	structure: CategoryStructure = CategoryStructure.instance;
}
