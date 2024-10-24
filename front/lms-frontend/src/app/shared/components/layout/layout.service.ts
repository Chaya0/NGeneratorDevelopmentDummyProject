import {effect, Injectable, signal} from '@angular/core';
import {Subject} from 'rxjs';

export interface AppConfig {
  inputStyle: string;
  ripple: boolean;
  menuMode: string;
  scale: number;
  darkMode: boolean;
}

interface LayoutState {
  staticMenuDesktopInactive: boolean;
  overlayMenuActive: boolean;
  profileSidebarVisible: boolean;
  configSidebarVisible: boolean;
  staticMenuMobileActive: boolean;
  menuHoverActive: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class LayoutService {
  _config: AppConfig = {
    ripple: true,
    inputStyle: 'outlined',
    menuMode: 'static',
    scale: 14,
    darkMode: false,
  };
  config = signal<AppConfig>(this._config);
  state: LayoutState = {
    staticMenuDesktopInactive: false,
    overlayMenuActive: false,
    profileSidebarVisible: false,
    configSidebarVisible: false,
    staticMenuMobileActive: false,
    menuHoverActive: false,
  };
  private configUpdate = new Subject<AppConfig>();
  private overlayOpen = new Subject<any>();
  configUpdate$ = this.configUpdate.asObservable();
  overlayOpen$ = this.overlayOpen.asObservable();

  constructor() {
    effect(() => {
      const config = this.config();
      this.changeTheme();
      this.changeScale(config.scale);
      this.onConfigUpdate();
    });
  }

  updateStyle(config: AppConfig) {
    return (
      config.darkMode !== this._config.darkMode
    );
  }

  onMenuToggle() {
    // States are changed via [(ngModel)] in topbar component.
    if (this.isOverlay()) {
      // this.state.overlayMenuActive = !this.state.overlayMenuActive;
      if (this.state.overlayMenuActive) {
        this.overlayOpen.next(null);
      }
    }
    if (this.isDesktop()) {
      // this.state.staticMenuDesktopInactive = !this.state.staticMenuDesktopInactive;
    } else {
      // this.state.staticMenuMobileActive = !this.state.staticMenuMobileActive;
      if (this.state.staticMenuMobileActive) {
        this.overlayOpen.next(null);
      }
    }
  }

  showProfileSidebar() {
    this.state.profileSidebarVisible = !this.state.profileSidebarVisible;
    if (this.state.profileSidebarVisible) {
      this.overlayOpen.next(null);
    }
  }

  showConfigSidebar() {
    this.state.configSidebarVisible = true;
  }

  isOverlay() {
    return this.config().menuMode === 'overlay';
  }

  isDesktop() {
    return window.innerWidth > 991;
  }
  onConfigUpdate() {
    this._config = {...this.config()};
    this.configUpdate.next(this.config());
  }

  changeTheme() {
    let newHref;
    if(this.config().darkMode){
      newHref = 'assets/layout/styles/themes/lara-dark-blue/theme.css'
      this.replaceThemeLink(newHref)
    }else{
      newHref = 'assets/layout/styles/themes/lara-light-blue/theme.css'
      this.replaceThemeLink(newHref)
    }
  }

  replaceThemeLink(href: string) {
    const id = 'theme-css';
    let themeLink = <HTMLLinkElement>document.getElementById(id);
    const cloneLinkElement = <HTMLLinkElement>themeLink.cloneNode(true);

    cloneLinkElement.setAttribute('href', href);
    cloneLinkElement.setAttribute('id', id + '-clone');

    themeLink.parentNode!.insertBefore(
      cloneLinkElement,
      themeLink.nextSibling
    );
    cloneLinkElement.addEventListener('load', () => {
      themeLink.remove();
      cloneLinkElement.setAttribute('id', id);
    });
  }

  changeScale(value: number) {
    document.documentElement.style.fontSize = `${value}px`;
  }

  updateFromLocalStorage(styleConfig: any) {
    this.config.update((config) => ({
      ...config,
      scale: styleConfig.scale ? styleConfig.scale : config.scale,
      inputStyle: styleConfig.inputStyle ? styleConfig.inputStyle : config.inputStyle,
      darkMode: styleConfig.darkMode ? styleConfig.darkMode : config.darkMode,
      menuMode: styleConfig.menuMode ? styleConfig.menuMode : config.menuMode,
    }));
  }
}
