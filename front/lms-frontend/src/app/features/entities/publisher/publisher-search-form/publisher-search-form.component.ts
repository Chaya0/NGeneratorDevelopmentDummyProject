import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { PublisherStructure } from '../publisher-structure';

@Component({
	selector: 'app-publisher-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './publisher-search-form.component.html',
	styleUrl: './publisher-search-form.component.css'
})

export class PublisherSearchFormComponent {
	@Input() structure!: PublisherStructure;
}
