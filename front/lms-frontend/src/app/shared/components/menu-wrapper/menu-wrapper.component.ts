import {Component, OnInit} from '@angular/core';
import {MenuItemComponent} from "../menu-item/menu-item.component";
import {CommonModule} from '@angular/common';
import {PrimeModule} from '../../prime/prime.modules';
import { MENU_MODEL } from './menu.model';

@Component({
  selector: 'app-menu-wrapper',
  standalone: true,
  imports: [MenuItemComponent, CommonModule, PrimeModule],
  templateUrl: './menu-wrapper.component.html',
  styleUrl: './menu-wrapper.component.scss'
})
export class MenuWrapperComponent implements OnInit {

  model: any[] = [];

  ngOnInit() {
    this.model = MENU_MODEL;
  }
}
