import {Component, inject, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {RootComponent} from "../root/root.component";
import {Attribute} from "../../entity-utils/attribute";
import {AppUtils} from "../../../shared/utils/app-utils";
import {Structure} from "../../../features/entities/structure";
import {TranslationService} from "../../services/translation.service";
import {FormUtils} from "../../../shared/utils/form-utils";

@Component({
  selector: 'app-generic-form',
  standalone: true,
  imports: [],
  template: '',
  styles: []
})
export abstract class GenericFormComponent extends RootComponent implements OnInit {
  @Input() structure?: Structure;
  entity: string = "";
  attributes: Attribute[] = [];
  form?: FormGroup;
  options: Map<string, any[]> = new Map<string, any[]>();
  pickListTarget: Map<string, any[]> = new Map<string, any[]>();
  pickListSource: Map<string, any[]> = new Map<string, any[]>();
  protected fb: FormBuilder = inject(FormBuilder);
  protected translationService = inject(TranslationService);
  protected formData: {} = {};

  ngOnInit() {
    this.attributes = this.initAttributes();
    this.entity = this.structure?.entityName ?? "";
    this.initForm(this.formData);
    this.initOptions();
    this.setupValueChangeHandlers();
  }

  abstract initOptions(): void;

  abstract initForm(formData: any): void;

  getEnumOptions(attr_name: string) {
    return FormUtils.getEnumOptions(attr_name, this.translationService);
  }

  getMultiSelectOptions(attr: Attribute) {
    let options = this.options.get(attr.attr_name);
    if (options) {
      return options.map(e => {
        return {
          label: AppUtils.getDisplayValue(e, attr.type),
          value: e
        }
      });
    }
    return [];
  }

  public abstract getPlaceholder(attr: Attribute): string;

  protected initAttributes() {
    return this.structure?.attributes || [];
  }

  private setupValueChangeHandlers(): void {
    this.attributes.forEach(attr => {
      const control = this.form?.controls[attr.attr_name];
      control?.valueChanges.subscribe(value => {
        if (value === '' || value === undefined) {
          control.setValue(null, {emitEvent: false});
        }
      });
    });
  }
}
