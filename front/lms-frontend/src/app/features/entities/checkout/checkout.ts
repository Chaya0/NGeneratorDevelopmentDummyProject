import { User } from '../user/user';
import { Book } from '../book/book';
import { CheckoutStatus } from '../enums.model';

export class Checkout {
	checkoutDate!: Date;
	dueDate!: Date;
	returnDate!: Date;
	checkoutStatus!: CheckoutStatus;
	user!: User;
	book!: Book;
}
