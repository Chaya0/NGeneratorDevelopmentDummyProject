import { Component, Input } from '@angular/core';
import { GenericSearchFormComponent } from '../../../../core/components/generic-search-form/generic-search-form.component';
import { FineStructure } from '../fine-structure';

@Component({
	selector: 'app-fine-search-form',
	standalone: true,
	imports: [ GenericSearchFormComponent ],
	templateUrl: './fine-search-form.component.html',
	styleUrl: './fine-search-form.component.css'
})

export class FineSearchFormComponent {
	@Input() structure!: FineStructure;
}
