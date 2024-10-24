import { LibraryStructure } from "./library/library-structure";
import { Library } from "./library/library";
import { BookStructure } from "./book/book-structure";
import { Book } from "./book/book";
import { AuthorStructure } from "./author/author-structure";
import { Author } from "./author/author";
import { PublisherStructure } from "./publisher/publisher-structure";
import { Publisher } from "./publisher/publisher";
import { CategoryStructure } from "./category/category-structure";
import { Category } from "./category/category";
import { CheckoutStructure } from "./checkout/checkout-structure";
import { Checkout } from "./checkout/checkout";
import { FineStructure } from "./fine/fine-structure";
import { Fine } from "./fine/fine";
import { UserStructure } from "./user/user-structure";
import { User } from "./user/user";
import { ReservationStructure } from "./reservation/reservation-structure";
import { Reservation } from "./reservation/reservation";

export const entities = [ 
	{
		name: "Library",
		structure: LibraryStructure.instance,
		type: Library
	},
	{
		name: "Book",
		structure: BookStructure.instance,
		type: Book
	},
	{
		name: "Author",
		structure: AuthorStructure.instance,
		type: Author
	},
	{
		name: "Publisher",
		structure: PublisherStructure.instance,
		type: Publisher
	},
	{
		name: "Category",
		structure: CategoryStructure.instance,
		type: Category
	},
	{
		name: "Checkout",
		structure: CheckoutStructure.instance,
		type: Checkout
	},
	{
		name: "Fine",
		structure: FineStructure.instance,
		type: Fine
	},
	{
		name: "User",
		structure: UserStructure.instance,
		type: User
	},
	{
		name: "Reservation",
		structure: ReservationStructure.instance,
		type: Reservation
	},
]
