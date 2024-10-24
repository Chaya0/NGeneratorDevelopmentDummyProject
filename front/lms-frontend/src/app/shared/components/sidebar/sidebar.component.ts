import {Component, ElementRef} from '@angular/core';
import {MenuItemComponent} from "../menu-item/menu-item.component";
import {PrimeModule} from '../../prime/prime.modules';
import {MenuWrapperComponent} from "../menu-wrapper/menu-wrapper.component";

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [MenuItemComponent, PrimeModule, MenuWrapperComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  constructor(public el: ElementRef) {
  }

}
