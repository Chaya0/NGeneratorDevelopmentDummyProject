import {AttributeFieldType} from "./search-field";

export class InputField {
  type: AttributeFieldType;
  _multiSelect: boolean;
  _required: boolean;
  _disabled: boolean;
  _maxLength: any | null;
  _minLength: any | null;
  _trim: boolean;
  _toLoverCase: boolean;

  constructor(type: AttributeFieldType = AttributeFieldType.INPUT, multiSelect: boolean = false, required: boolean = false, disabled: boolean = false, maxLength: number | Date | null = null, minLength: number | Date | null = null, trim: boolean = false, toLoverCase: boolean = false) {
    this.type = type;
    this._multiSelect = multiSelect;
    this._required = required;
    this._disabled = disabled;
    this._maxLength = maxLength;
    this._minLength = minLength;
    this._trim = trim
    this._toLoverCase = toLoverCase
  }

  static INPUT(): InputField {
    return new InputField();
  }

  static DROPDOWN(): InputField {
    return new InputField(AttributeFieldType.DROPDOWN);
  }

  static PICK_LIST(): InputField {
    return new InputField(AttributeFieldType.PICK_LIST, true);
  }

  static TREE_SELECT(): InputField {
    return new InputField(AttributeFieldType.TREE_SELECT);
  }

  static CALENDAR(): InputField {
    return new InputField(AttributeFieldType.CALENDAR);
  }

  static CHECKBOX(): InputField {
    return new InputField(AttributeFieldType.CHECKBOX);
  }

  isPickList() {
    return this.type === AttributeFieldType.PICK_LIST;
  }

  isTreeSelect() {
    return this.type === AttributeFieldType.TREE_SELECT;
  }

  isDropdown() {
    return this.type === AttributeFieldType.DROPDOWN;
  }

  isCheckbox() {
    return this.type === AttributeFieldType.CHECKBOX;
  }

  isCalendar() {
    return this.type === AttributeFieldType.CALENDAR;
  }

  isInput() {
    return this.type === AttributeFieldType.INPUT;
  }

  maxLength(value: any) {
    this._maxLength = value;
    return this;
  }

  minLength(value: any) {
    this._minLength = value;
    return this;
  }

  multiSelect(value: boolean) {
    this._multiSelect = value;
    return this;
  }

  required(value: boolean) {
    this._required = value;
    return this;
  }

  disabled(value: boolean) {
    this._disabled = value;
    return this;
  }

  trim(value: boolean) {
    this._trim = value;
    return this;
  }

  toLoverCase(value: boolean) {
    this._toLoverCase = value;
    return this;
  }
}
