import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class LibraryStructure implements Structure {

	static #instance: LibraryStructure;

	entityName: string = 'Library';
	title: string = 'library';
	fkSearchAttribute: string[] = [''];

	attributes: Attribute[] = [
		new Attribute(
			'name',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'address',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'city',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'phone',
			'string',
			SearchField.INPUT(),
			InputField.INPUT()
		),
	];


	private constructor() {}
	public static get instance(): LibraryStructure {
		if(!LibraryStructure.#instance) {
			LibraryStructure.#instance = new LibraryStructure();
		}
		return LibraryStructure.#instance;
	}
}
