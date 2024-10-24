import {SearchFilter} from "./search-filter";
import {LogicalOperator} from "./logical-operator";

export interface FilterGroup {
  logicalOperator: LogicalOperator;
  filters: SearchFilter[];
}
