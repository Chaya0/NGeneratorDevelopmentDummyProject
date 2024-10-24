import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { LibraryStructure } from '../library-structure';

@Component({
	selector: 'app-library-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './library-search-form.component.html',
	styleUrl: './library-search-form.component.css'
})

export class LibrarySearchFormComponent {
	@Input() structure!: LibraryStructure;
}
