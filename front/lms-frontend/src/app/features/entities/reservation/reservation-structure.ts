import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class ReservationStructure implements Structure {

	static #instance: ReservationStructure;

	entityName: string = 'Reservation';
	title: string = 'reservation';
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
			'reservationDate',
			'localDate',
			SearchField.CALENDAR().maxLength(new Date()),
			InputField.CALENDAR().maxLength(new Date()).required(true)
		),
		new Attribute(
			'status',
			'enum',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN().required(true)
		),
	];


	private constructor() {}
	public static get instance(): ReservationStructure {
		if(!ReservationStructure.#instance) {
			ReservationStructure.#instance = new ReservationStructure();
		}
		return ReservationStructure.#instance;
	}
}
