export enum AttributeFieldType {
  INPUT = "INPUT",
  DROPDOWN = "DROPDOWN",
  TREE_SELECT = "TREE_SELECT",
  PICK_LIST = "PICK_LIST",
  CALENDAR = "CALENDAR",
  CHECKBOX = "CHECKBOX",
}

export class SearchField {
  type: AttributeFieldType;
  _multiSelect: boolean;
  _maxLength: number | Date | null;
  _minLength: number | Date | null;
  _required: boolean;
  _rangedCalendar: boolean = false;
  _rangedFromName: string = "dateFrom";
  _rangedToName: string = "dateTo";
  _hasDefaultValue: boolean;

  constructor(type: AttributeFieldType = AttributeFieldType.INPUT, multiSelect: boolean = false, maxLength: number | Date | null = null, minLength: number | Date | null = null, required: boolean = false, hasDefaultValue = false) {
    this.type = type;
    this._multiSelect = multiSelect;
    this._maxLength = maxLength;
    this._minLength = minLength;
    this._required = required;
    this._hasDefaultValue = hasDefaultValue
  }

  static INPUT(): SearchField {
    return new SearchField();
  }

  static DROPDOWN(): SearchField {
    return new SearchField(AttributeFieldType.DROPDOWN);
  }

  static PICK_LIST(): SearchField {
    return new SearchField(AttributeFieldType.PICK_LIST, true);
  }

  static TREE_SELECT(): SearchField {
    return new SearchField(AttributeFieldType.TREE_SELECT, true);
  }

  static CALENDAR(ranged: boolean = false) {
    return new SearchField(AttributeFieldType.CALENDAR, ranged);
  }

  static CHECKBOX() {
    return new SearchField(AttributeFieldType.CHECKBOX);
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

  initDefaultValue(value: boolean){
    this._hasDefaultValue = value;
    return this;
  }

  hasDefaultValue() {
    return this._hasDefaultValue;
  }

  required(value: boolean) {
    this._required = value;
    return this;
  }

  ranged(value: boolean, fromName: string = "dateFrom", toName: string = "dateTo") {
    if (this.isCalendar()) {
      this._rangedCalendar = value;
      this._rangedFromName = fromName;
      this._rangedToName = toName;
    }
    return this;
  }

  getMaxLengthDate() {
    return this._maxLength as Date;
  }

  getMinLengthDate() {
    return this._minLength as Date;
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

  isRanged() {
    return this.isCalendar() && this._rangedCalendar;
  }
}
