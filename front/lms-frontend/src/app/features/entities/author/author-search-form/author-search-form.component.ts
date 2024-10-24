import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { AuthorStructure } from '../author-structure';

@Component({
	selector: 'app-author-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './author-search-form.component.html',
	styleUrl: './author-search-form.component.css'
})

export class AuthorSearchFormComponent {
	@Input() structure!: AuthorStructure;
}
