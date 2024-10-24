import { User } from '../user/user';
import { Checkout } from '../checkout/checkout';

export class Fine {
	fineAmount!: number;
	paid!: boolean;
	fineDate!: Date;
	user!: User;
	checkout!: Checkout;
}
