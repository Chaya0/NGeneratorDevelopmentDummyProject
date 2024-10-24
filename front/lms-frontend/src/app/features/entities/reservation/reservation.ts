import { User } from '../user/user';
import { Book } from '../book/book';
import { ReservationStatus } from '../enums.model';

export class Reservation {
	reservationDate!: Date;
	reservationStatus!: ReservationStatus;
	user!: User;
	book!: Book;
}
