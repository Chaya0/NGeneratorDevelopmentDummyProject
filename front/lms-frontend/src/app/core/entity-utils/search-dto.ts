import {FilterGroup} from "./filter-group";
import {Order} from "./order";

export interface SearchDTO {
  pageNumber: number;
  pageSize: number;
  sort: Order[];
  filterGroup: FilterGroup;
}
