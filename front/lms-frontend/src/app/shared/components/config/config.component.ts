import {Component, inject, Input} from '@angular/core';
import {LayoutService} from '../layout/layout.service';
import {PrimeModule} from '../../prime/prime.modules';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-config',
  standalone: true,
  imports: [PrimeModule, FormsModule],
  templateUrl: './config.component.html',
  styleUrl: './config.component.scss'
})
export class ConfigComponent {
  @Input() minimal: boolean = false;
  scales: number[] = [12, 13, 14, 15, 16];

  public layoutService = inject(LayoutService)

  get visible(): boolean {
    return this.layoutService.state.configSidebarVisible;
  }

  set visible(_val: boolean) {
    this.layoutService.state.configSidebarVisible = _val;
  }

  get scale(): number {
     
    return this.layoutService.config().scale;
  }

  set scale(_val: number) {
    this.layoutService.config.update((config) => ({
      ...config,
      scale: _val,
    }));
  }

  get menuMode(): string {
    return this.layoutService.config().menuMode;
  }

  set menuMode(_val: string) {
    this.layoutService.config.update((config) => ({
      ...config,
      menuMode: _val,
    }));
  }

  get inputStyle(): string {
    return this.layoutService.config().inputStyle;
  }

  set inputStyle(_val: string) {
    this.layoutService.config().inputStyle = _val;
  }

  get ripple(): boolean {
    return this.layoutService.config().ripple;
  }

  set ripple(_val: boolean) {
    this.layoutService.config.update((config) => ({
      ...config,
      ripple: _val,
    }));
  }

  get darkMode(): boolean {
    return this.layoutService.config().darkMode;
  }

  set darkMode(_val: boolean) {
    this.layoutService.config.update((config) => ({
      ...config,
      darkMode: _val,
    }));
  }

  decrementScale() {
    this.scale--;
  }

  incrementScale() {
    this.scale++;
  }
}
