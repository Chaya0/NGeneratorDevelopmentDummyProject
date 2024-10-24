import { Component, inject, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { UserUpdateFormComponent } from '../../../features/entities/user/user-update-form/user-update-form.component';
import { UserStructure } from '../../../features/entities/user/user-structure';
import { BreadcrumbComponent } from "../../../shared/components/breadcrumb/breadcrumb.component";

@Component({
	selector: 'app-user-edit-page',
	standalone: true,
	imports: [UserUpdateFormComponent, BreadcrumbComponent],
	templateUrl: './user-edit-page.component.html',
	styleUrl: './user-edit-page.component.css'
})

export class UserEditPageComponent implements OnInit {
	structure: UserStructure = UserStructure.instance;
	id: any;
	route = inject(ActivatedRoute);

	ngOnInit() {
		this.route.paramMap.subscribe(params => {
			this.id = params.get('id');
		});
	}
}
