import { Component } from '@angular/core';
import { UserInsertFormComponent } from '../../../features/entities/user/user-insert-form/user-insert-form.component';
import { UserStructure } from '../../../features/entities/user/user-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-user-create-page',
	standalone: true,
	imports: [UserInsertFormComponent, BreadcrumbComponent],
	templateUrl: './user-create-page.component.html',
	styleUrl: './user-create-page.component.css'
})

export class UserCreatePageComponent {
	structure: UserStructure = UserStructure.instance;
}
