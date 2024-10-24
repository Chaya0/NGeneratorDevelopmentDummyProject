import {Component, inject, Input} from '@angular/core';
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {GenericFormComponent} from "../generic-form/generic-form.component";
import {AppUtils} from "../../../shared/utils/app-utils";
import {PrimeModule} from "../../../shared/prime/prime.modules";
import {ToastService} from "../../services/toast.service";
import {Router} from "@angular/router";
import {Attribute} from "../../entity-utils/attribute";
import {FormUtils} from "../../../shared/utils/form-utils";
import {AppDate} from "../../../shared/utils/app-date";

@Component({
  selector: 'app-generic-update-form',
  standalone: true,
  imports: [
    AsyncPipe,
    NgForOf,
    NgIf,
    PrimeModule,
    ReactiveFormsModule
  ],
  templateUrl: '../generic-form/generic-form.component.html',
  styleUrl: '../generic-form/generic-form.component.scss'
})
export class GenericUpdateFormComponent extends GenericFormComponent {
  @Input() id: any;
  override fb: FormBuilder = inject(FormBuilder);
  protected toastService = inject(ToastService);
  protected router = inject(Router);
  protected data: { [key: string]: any; } = {};
  protected readonly AppUtils = AppUtils;

  override ngOnInit() {
    super.ngOnInit();
    this.initPickListTarget();
  }

  onSubmit(): void {
    if (!this.form?.valid) {
      this.form?.markAllAsTouched();
      return;
    }

    for (let attr of this.attributes) {
      if (attr.inputField?.isPickList()) {
        this.data[attr.attr_name] = this.pickListTarget.get(attr.attr_name);
      } else if (attr.inputField?.isTreeSelect()) {
        if (this.form.controls[attr.attr_name].value)
          this.data[attr.attr_name] = this.form.controls[attr.attr_name].value.data;
        else
          this.data[attr.attr_name] = null;
      } else if (attr.type === 'datetime-local') {
        this.data[attr.attr_name] = AppDate.from(this.form.controls[attr.attr_name].value).toString();
      } else {
        let value = this.form.controls[attr.attr_name].value
        if(attr.inputField?._trim){
          value = value.toString().trim();
        } 
        if(attr.inputField?._toLoverCase){
          value = value.toString().toLoverCase();
        }
        this.data[attr.attr_name] = value;
      }
    }

    this.update(this.data, this.entity, () => {
      this.toastService.showSuccess('updated_successfully');
      this.router.navigate(['/' + AppUtils.toFirstLetterLowercase(this.entity)]).then();
    });
  }

  override initForm(formData: any): void {
    const formControls: { [key: string]: any } = this.attributes.reduce((controls, attr) => {
      // @ts-ignore
      controls[attr.attr_name] = [formData[attr.attr_name] || null, FormUtils.getInputValidators(attr)];
      return controls;
    }, {});
    this.form = this.fb.group(formControls);
  }

  override initOptions(): void {
    this.getById(this.id, this.entity, (data: { [key: string]: any; }) => {
      let populatedData = this.populateFormData(data);
      this.data = populatedData;
      this.form?.patchValue(populatedData);
    });
  }

  getPlaceholder(attr: Attribute): string {
    return this.translationService.translate(AppUtils.toFirstLetterLowercase(this.entity) + '.' + attr.attr_name).concat(" ", attr.inputField?._required ? '*' : '');
  }

  initPickListTarget(): void {
    for (let attr of this.attributes) {
      if (!attr.isForeignKey()) continue;
      if (attr.inputField?.isPickList()) {
        this.pickListTarget.set(attr.attr_name, this.data[attr.attr_name]);
      }
    }
  }

  protected populateFormData(data: { [p: string]: any }) {
    this.handleDates(data);
    for (let attr of this.attributes) {
      if (!attr.isForeignKey()) continue;
      if (attr.inputField?.isPickList()) {
        this.getAll(attr.type, (pickListData: any[]) => {
          this.pickListTarget.set(attr.attr_name, this.data[attr.attr_name]);
          this.pickListSource.set(attr.attr_name, pickListData.filter((item: any) => {
              return !this.data[attr.attr_name].some((dataItem: any) => dataItem.id === item.id)
            })
          )
        });
      } else if (attr.inputField?.isTreeSelect()) {
        let selectedItem = {
          label: AppUtils.getDisplayValue(data[attr.attr_name], attr.type),
          data: data[attr.attr_name],
        }
        data[attr.attr_name] = selectedItem.label && selectedItem.data ? selectedItem : null;
        this.getAll(attr.type, (data: any[]) => {
          this.options.set(attr.attr_name, FormUtils.convertDataToTreeNodes(data, attr, this.structure?.attributes || []));
        });
      } else if (attr.inputField?.isDropdown()) {
        this.getAll(attr.type, (data: any[]) => {
          this.options.set(attr.attr_name, data.map(e => {
            return {
              data: e,
              label: AppUtils.getDisplayValue(e, attr.type)
            }
          }));
        });
      } else {
        this.getAll(attr.type, (data: any[]) => {
          this.options.set(attr.attr_name, data);
        });
      }
    }
    return data;
  }

  protected override initAttributes(): Attribute[] {
    return this.structure?.attributes.filter(attr => attr.inputField) ?? [];
  }

  protected prepareUpdateBody(form: { [key: string]: any; }): { [key: string]: any; } {
    let body: { [key: string]: any; } = {};
    for (let attr of this.attributes) {
      if (attr.type === 'datetime-local' && form[attr.attr_name]) {
        const date = new Date(form['controls'][attr.attr_name]);
        body[attr.attr_name] = AppDate.from(date).toString();
      } else if (attr.inputField?.isTreeSelect()) {
        body[attr.attr_name] = form['controls'][attr.attr_name].value.data;
      } else if (attr.inputField?.isPickList()) {
        this.data[attr.attr_name] = this.pickListTarget.get(attr.attr_name);
      } else {
        body[attr.attr_name] = form['controls'][attr.attr_name].value;
      }
    }
    return body;
  }

  protected handleDates(data: { [p: string]: any }) {
    for (let attr of this.attributes) {
      if ((attr.type === 'datetime-local' || attr.type === 'date') && data[attr.attr_name]) {
        data[attr.attr_name] = new Date(data[attr.attr_name]);
      }
    }
  }
}
