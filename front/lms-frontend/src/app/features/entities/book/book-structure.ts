import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class BookStructure implements Structure {

	static #instance: BookStructure;

	entityName: string = 'Book';
	title: string = 'book';
	fkSearchAttribute: string[] = [''];

	attributes: Attribute[] = [
		new Attribute(
			'author',
			'Author',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'publisher',
			'Publisher',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'category',
			'Category',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'library',
			'Library',
			SearchField.DROPDOWN(),
			InputField.DROPDOWN()
		),
		new Attribute(
			'title',
			'string',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'isbn',
			'string',
			SearchField.INPUT(),
			InputField.INPUT()
		),
		new Attribute(
			'publishedDate',
			'localDate',
			SearchField.CALENDAR().maxLength(new Date()),
			InputField.CALENDAR().maxLength(new Date())
		),
		new Attribute(
			'language',
			'string',
			SearchField.INPUT(),
			InputField.INPUT()
		),
		new Attribute(
			'availableCopies',
			'integer',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
		new Attribute(
			'totalCopies',
			'integer',
			SearchField.INPUT(),
			InputField.INPUT().required(true)
		),
	];


	private constructor() {}
	public static get instance(): BookStructure {
		if(!BookStructure.#instance) {
			BookStructure.#instance = new BookStructure();
		}
		return BookStructure.#instance;
	}
}
