import {NgModule} from '@angular/core';

import {MenubarModule} from 'primeng/menubar';
import {SidebarModule} from 'primeng/sidebar';
import {Button, ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {PanelMenuModule} from 'primeng/panelmenu';
import {AvatarModule} from 'primeng/avatar';
import {AvatarGroupModule} from 'primeng/avatargroup';
import {RippleModule} from 'primeng/ripple';
import {StyleClassModule} from 'primeng/styleclass';
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputSwitchModule} from 'primeng/inputswitch';
import {CommonModule} from '@angular/common';
import {TranslatePipe} from '../../core/pipes/translate.pipe';
import {DropdownModule} from 'primeng/dropdown';
import {TableModule} from 'primeng/table';
import {ProgressBarModule} from 'primeng/progressbar';
import {SliderModule} from 'primeng/slider';
import {MultiSelectModule} from 'primeng/multiselect';
import {InputTextModule} from 'primeng/inputtext';
import {RatingModule} from 'primeng/rating';
import {ToastModule} from 'primeng/toast';
import {ToggleButtonModule} from 'primeng/togglebutton';
import {PaginatorModule} from 'primeng/paginator';
import {SkeletonModule} from 'primeng/skeleton';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import {SplitButtonModule} from 'primeng/splitbutton';
import {TreeTableModule} from 'primeng/treetable';
import {ChartModule} from 'primeng/chart';
import {TagModule} from 'primeng/tag';
import {TableStyleClassDirective} from './table-style-class.directive';
import {CalendarModule} from 'primeng/calendar';
import {CheckboxModule} from 'primeng/checkbox';
import {TreeSelectModule} from 'primeng/treeselect';
import {ToolbarModule} from "primeng/toolbar";
import {BreadcrumbComponent} from "../components/breadcrumb/breadcrumb.component";
import {CardModule} from "primeng/card";
import {DialogModule} from "primeng/dialog";
import {MessageModule} from "primeng/message";
import {OrganizationChartModule} from 'primeng/organizationchart';
import {FloatLabelModule} from "primeng/floatlabel";
import {PickListModule} from 'primeng/picklist';
import {StepperModule} from "primeng/stepper";
import {DividerModule} from "primeng/divider";
import {TriStateCheckboxModule} from "primeng/tristatecheckbox";
import {ChipModule} from "primeng/chip";
import {PasswordModule} from "primeng/password";

let all = [
  MenubarModule,
  SidebarModule,
  ButtonModule,
  MenuModule,
  PanelMenuModule,
  AvatarModule,
  AvatarGroupModule,
  RippleModule,
  StyleClassModule,
  RadioButtonModule,
  InputSwitchModule,
  CommonModule,
  TranslatePipe,
  DropdownModule,
  TableModule,
  ProgressBarModule,
  SliderModule,
  MultiSelectModule,
  RatingModule,
  InputTextModule,
  ToggleButtonModule,
  ToastModule,
  PaginatorModule,
  SkeletonModule,
  ProgressSpinnerModule,
  SplitButtonModule,
  TreeTableModule,
  ChartModule,
  TagModule,
  ToastModule,
  CalendarModule,
  CheckboxModule,
  TreeSelectModule,
  MenuModule,
  Button,
  ToolbarModule,
  InputTextModule,
  BreadcrumbComponent,
  CardModule,
  DialogModule,
  ToastModule,
  RippleModule,
  MessageModule,
  OrganizationChartModule,
  MessageModule,
  CalendarModule,
  CheckboxModule,
  MultiSelectModule,
  TreeSelectModule,
  DropdownModule,
  ToastModule,
  FloatLabelModule,
  PickListModule,
  StepperModule,
  DividerModule,
  TriStateCheckboxModule,
  ChipModule,
  PasswordModule,
]

@NgModule({
  declarations: [TableStyleClassDirective],
  imports: [all
    // Add more modules as needed
  ],
  exports: [all,
    // Export the same modules
    TableStyleClassDirective
  ],
  providers: [],
})

export class PrimeModule {
}
