import {Attribute} from '../../core/entity-utils/attribute';

export interface Structure {
	entityName: string;
	title: string;
	fkSearchAttribute: string[];
	attributes: Attribute[];
}
