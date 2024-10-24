import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { BookStructure } from '../book-structure';

@Component({
	selector: 'app-book-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './book-search-form.component.html',
	styleUrl: './book-search-form.component.css'
})

export class BookSearchFormComponent {
	@Input() structure!: BookStructure;
}
