import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class FineStructure implements Structure {

	static #instance: FineStructure;

	entityName: string = 'Fine';
	title: string = 'fine';
	fkSearchAttribute: string[] = [''];

	attributes: Attribute[] = [
		new Attribute(
			'user',
			'User',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'checkout',
			'Checkout',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'fineAmount',
			'double',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'paid',
			'boolean',
			SearchField.CHECKBOX(),
			InputField.CHECKBOX().required(true)
		),
		new Attribute(
			'fineDate',
			'localDate',
			SearchField.CALENDAR().maxLength(new Date()),
			InputField.CALENDAR().maxLength(new Date()).required(true)
		),
	];


	private constructor() {}
	public static get instance(): FineStructure {
		if(!FineStructure.#instance) {
			FineStructure.#instance = new FineStructure();
		}
		return FineStructure.#instance;
	}
}
