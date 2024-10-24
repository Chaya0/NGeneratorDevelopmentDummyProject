import {OrderDirection} from "./order-direction";

export class Order {
  property?: string;
  direction?: OrderDirection;

  constructor(property: string, direction: string) {
    this.direction = this.stringToOrderDirection(direction);
    this.property = property;
  }

  private stringToOrderDirection(value: string): OrderDirection {
    switch (value.toUpperCase()) {
      case OrderDirection.ASC:
        return OrderDirection.ASC;
      case OrderDirection.DESC:
        return OrderDirection.DESC;
      default:
        return OrderDirection.ASC;
    }
  }
}

