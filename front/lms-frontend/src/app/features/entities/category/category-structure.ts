import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class CategoryStructure implements Structure {

	static #instance: CategoryStructure;

	entityName: string = 'Category';
	title: string = 'category';
	fkSearchAttribute: string[] = [''];

	attributes: Attribute[] = [
		new Attribute(
			'name',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
	];


	private constructor() {}
	public static get instance(): CategoryStructure {
		if(!CategoryStructure.#instance) {
			CategoryStructure.#instance = new CategoryStructure();
		}
		return CategoryStructure.#instance;
	}
}
