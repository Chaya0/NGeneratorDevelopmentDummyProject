import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class UserStructure implements Structure {

	static #instance: UserStructure;

	entityName: string = 'User';
	title: string = 'user';
	fkSearchAttribute: string[] = [''];

	attributes: Attribute[] = [
		new Attribute(
			'username',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'password',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'email',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
	];


	private constructor() {}
	public static get instance(): UserStructure {
		if(!UserStructure.#instance) {
			UserStructure.#instance = new UserStructure();
		}
		return UserStructure.#instance;
	}
}
