import { Author } from '../author/author';
import { Publisher } from '../publisher/publisher';
import { Category } from '../category/category';
import { Library } from '../library/library';

export class Book {
	title!: string;
	isbn!: string;
	publishedDate!: Date;
	language!: string;
	availableCopies!: number;
	totalCopies!: number;
	author!: Author;
	publisher!: Publisher;
	category!: Category;
	library!: Library;
}
