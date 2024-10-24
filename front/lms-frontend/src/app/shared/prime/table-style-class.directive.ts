import { Directive } from '@angular/core';
import { Table } from 'primeng/table';

@Directive({
  selector: 'p-table'
})
export class TableStyleClassDirective {
  constructor(private table: Table) {
    this.table.styleClass = 'p-datatable-striped p-datatable-sm';
  }
}
