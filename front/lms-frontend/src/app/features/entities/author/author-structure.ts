import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class AuthorStructure implements Structure {

	static #instance: AuthorStructure;

	entityName: string = 'Author';
	title: string = 'author';
	fkSearchAttribute: string[] = [''];

	attributes: Attribute[] = [
		new Attribute(
			'firstName',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'lastName',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'birthDate',
			'localDate',
			SearchField.CALENDAR().maxLength(new Date()),
			InputField.CALENDAR().maxLength(new Date())
		),
	];


	private constructor() {}
	public static get instance(): AuthorStructure {
		if(!AuthorStructure.#instance) {
			AuthorStructure.#instance = new AuthorStructure();
		}
		return AuthorStructure.#instance;
	}
}
