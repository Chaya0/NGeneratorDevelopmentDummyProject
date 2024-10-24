import {SearchField} from "./search-field";
import {AppUtils} from "../../shared/utils/app-utils";
import {InputField} from "./input-field";

export class Attribute {
  attr_name: string;
  type: string;
  searchField: SearchField | null;
  inputField: InputField | null;

  constructor(attr_name: string, type: string, searchField: SearchField | null = null, inputField: InputField | null = null) {
    this.attr_name = attr_name;
    this.type = type;
    this.searchField = searchField;
    this.inputField = inputField;
  }

  public isEnum() {
    return this.type.toLowerCase() === 'enum';
  }

  public isForeignKey() {
    return AppUtils.isForeignKey(this.type);
  }
}
