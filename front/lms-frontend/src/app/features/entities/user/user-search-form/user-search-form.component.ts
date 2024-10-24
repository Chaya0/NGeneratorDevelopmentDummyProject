import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { UserStructure } from '../user-structure';

@Component({
	selector: 'app-user-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './user-search-form.component.html',
	styleUrl: './user-search-form.component.css'
})

export class UserSearchFormComponent {
	@Input() structure!: UserStructure;
}
