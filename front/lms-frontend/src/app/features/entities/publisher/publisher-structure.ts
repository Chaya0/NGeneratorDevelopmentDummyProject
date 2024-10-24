import {Attribute} from "../../../core/entity-utils/attribute";
import {Structure} from "../structure";
import {SearchField} from "../../../core/entity-utils/search-field";
import {InputField} from "../../../core/entity-utils/input-field";

export class PublisherStructure implements Structure {

	static #instance: PublisherStructure;

	entityName: string = 'Publisher';
	title: string = 'publisher';
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
			InputField.INPUT()
		),
		new Attribute(
			'website',
			'string',
			SearchField.INPUT(),
			InputField.INPUT()
		),
	];


	private constructor() {}
	public static get instance(): PublisherStructure {
		if(!PublisherStructure.#instance) {
			PublisherStructure.#instance = new PublisherStructure();
		}
		return PublisherStructure.#instance;
	}
}
