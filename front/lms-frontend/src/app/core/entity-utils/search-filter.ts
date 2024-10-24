import {SearchOperator} from "./search-operator";
import {LogicalOperator} from "./logical-operator";

export interface SearchFilter {
  key?: string;
  searchOperator?: SearchOperator;
  value?: any;
  logicalOperator?: LogicalOperator;
  filters?: SearchFilter[];
}
