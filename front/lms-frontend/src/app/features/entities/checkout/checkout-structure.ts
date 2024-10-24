import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class CheckoutStructure implements Structure {

	static #instance: CheckoutStructure;

	entityName: string = 'Checkout';
	title: string = 'checkout';
	fkSearchAttribute: string[] = [''];

	attributes: Attribute[] = [
		new Attribute(
			'user',
			'User',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'book',
			'Book',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'checkoutDate',
			'localDateTime',
			SearchField.CALENDAR().maxLength(new Date()),
			InputField.CALENDAR().maxLength(new Date()).required(true)
		),
		new Attribute(
			'dueDate',
			'localDate',
			SearchField.CALENDAR().maxLength(new Date()),
			InputField.CALENDAR().maxLength(new Date()).required(true)
		),
		new Attribute(
			'returnDate',
			'localDate',
			SearchField.CALENDAR().maxLength(new Date()),
			InputField.CALENDAR().maxLength(new Date())
		),
		new Attribute(
			'status',
			'enum',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN().required(true)
		),
	];


	private constructor() {}
	public static get instance(): CheckoutStructure {
		if(!CheckoutStructure.#instance) {
			CheckoutStructure.#instance = new CheckoutStructure();
		}
		return CheckoutStructure.#instance;
	}
}
