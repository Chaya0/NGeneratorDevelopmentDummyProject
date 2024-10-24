import {ChangeDetectionStrategy, Component, inject, Input} from '@angular/core';
import {AsyncPipe, NgFor, NgIf, NgTemplateOutlet} from "@angular/common";
import {GenericFormComponent} from "../generic-form/generic-form.component";
import {AppUtils} from "../../../shared/utils/app-utils";
import {SearchFilter} from "../../entity-utils/search-filter";
import {FilterGroup} from "../../entity-utils/filter-group";
import {LogicalOperator} from "../../entity-utils/logical-operator";
import {SearchDTO} from "../../entity-utils/search-dto";
import {SearchService} from "../../services/search.service";
import {SearchOperator} from "../../entity-utils/search-operator";
import {Page} from "../../entity-utils/page";
import {FormUtils} from "../../../shared/utils/form-utils";
import {Attribute} from "../../entity-utils/attribute";
import {TranslatePipe} from "../../pipes/translate.pipe";
import {PrimeModule} from "../../../shared/prime/prime.modules";
import {ReactiveFormsModule} from "@angular/forms";
import {ToastService} from "../../services/toast.service";
import {QueryService} from "../../services/query.service";
import {AppDate} from "../../../shared/utils/app-date";

@Component({
  selector: 'app-generic-search-form',
  standalone: true,
  imports: [NgIf, NgFor, NgTemplateOutlet, AsyncPipe, PrimeModule, TranslatePipe, ReactiveFormsModule],
  templateUrl: './generic-search-form.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['../generic-search-form/generic-search-form.component.scss', '../generic-form/generic-form.component.scss']
})
export class GenericSearchFormComponent extends GenericFormComponent {
  page?: Page;
  @Input() buttonSubmit: boolean = true;
  pageSize: number = 10;
  protected searchService = inject(SearchService);
  protected toastService = inject(ToastService);
  protected queryService = inject(QueryService);
  protected tableName: string = "";
  protected readonly AppUtils = AppUtils;

  onSubmit(): void {
    const searchDTO = this.prepareSearch();
    this.searchService.emitEvent(this.service.search(searchDTO, AppUtils.toFirstLetterLowercase(this.entity)));
    this.searchService.emitSearchEvent(true);
  }

  override initForm(formData: any) {
    this.tableName = this.structure?.title ?? this.entity;

    let dto = this.queryService.getFromStorage(this.tableName);
    const formControls: { [key: string]: any } = this.attributes.reduce((controls, attr) => {
      let defaultValue = dto ? FormUtils.extractValueFromSearchDTO(attr.attr_name, dto) : (formData[attr.attr_name] || null);
      if (defaultValue && attr.searchField?.isTreeSelect()) {
        defaultValue = this.createTreeValue(defaultValue, attr);
      }

      if (defaultValue && (attr.type === 'date' || attr.type === 'datetime-local') && !attr.searchField?.isRanged()) {
        defaultValue = new Date(defaultValue)
      } else if ((attr.type === 'date' || attr.type === 'datetime-local') && attr.searchField?.hasDefaultValue()) {
        defaultValue = new Date()
        if (attr.searchField?.isRanged()) {
          // This is needed because when there is validator for the max date, the validator will be initialized befor date to, so we are subtracting 1 second to handle this problem.
          let date = new Date()
          date.setMinutes(date.getSeconds() - 1);
          defaultValue = [AppDate.now().minusDays(7).toStartOfDay().toDate(), date];
        }
      }

      if (defaultValue !== null && attr.searchField?.isCheckbox()) {
        // @ts-ignore
        controls[attr.attr_name] = [defaultValue, FormUtils.getSearchValidators(attr)];
      } else {
        // @ts-ignore
        controls[attr.attr_name] = [defaultValue || null, FormUtils.getSearchValidators(attr)];
      }
      return controls;
    }, {});
    this.form = this.fb.group(formControls);
  }

  override getPlaceholder(attr: Attribute): string {
    return this.translationService.translate(AppUtils.toFirstLetterLowercase(this.entity) + '.' + attr.attr_name).concat(" ", attr.searchField?._required ? '*' : '');
  }

  override initOptions(): void {
    for (let attr of this.attributes) {
      if (!attr.isForeignKey()) continue;
      if (attr.searchField?.isTreeSelect()) {
        this.getAll(attr.type, (data: any[]) => {
          this.options.set(attr.attr_name, FormUtils.convertDataToTreeNodes(data, attr, this.structure?.attributes || []));
        });
      } else if (attr.searchField?.isDropdown() && !attr.searchField?._multiSelect && !attr.isEnum()) {
        this.getAll(attr.type, (data: any[]) => {
          this.options.set(attr.attr_name, data.map(e => {
            return {
              data: e,
              label: AppUtils.getDisplayValue(e, attr.type)
            }
          }));
        });
      } else {
        this.getAll(attr.type, (data: any[]) => {
          this.options.set(attr.attr_name, data);
        });
      }
    }
  }

  hasValue(attr: Attribute) {
    return !!this.form?.controls[attr.attr_name].value;
  }

  protected override initAttributes(): Attribute[] {
    return this.structure?.attributes.filter(attr => attr.searchField) ?? [];
  }

  protected prepareSearch() {
    const filters: SearchFilter[] = this.collectSearchFilters();
    const filterGroup: FilterGroup = {
      logicalOperator: LogicalOperator.AND,
      filters: filters.filter(f => f.value !== null && f.value !== undefined && f.value !== '')
    };

    let pageSizeSettings = AppUtils.DEFAULT_PAGE_SIZE;

    const searchDTO: SearchDTO = {
      pageNumber: 0,
      pageSize: Number.parseInt(pageSizeSettings),
      sort: [],
      filterGroup: filterGroup
    }
    if (searchDTO.pageSize > 100) {
      searchDTO.pageSize = 100;
    }
    // We are saving searchDTO inside the localstorage, so we can reuse it in the table component for page changes etc.
    this.queryService.saveToStorage(this.tableName, searchDTO);
    return searchDTO;
  }

  protected collectSearchFilters(): SearchFilter[] {
    return this.attributes.filter(attribute => {
      let filterValue = this.form?.get(attribute.attr_name)?.value;
      return !(attribute.isEnum() && filterValue?.length === 0);
    }).map(attribute => {
      let filterValue = this.form?.get(attribute.attr_name)?.value;

      if (typeof filterValue === 'string') {
        filterValue = filterValue.trim();
      }
      
      if (AppUtils.isForeignKey(attribute.type)) {
        return this.handleForeignKey(attribute, filterValue);
      }

      let searchOperatorValue = this.getSearchOperator(attribute, filterValue);
      filterValue = this.transformFilterValue(attribute, filterValue, searchOperatorValue);

      return {
        key: attribute.attr_name,
        searchOperator: searchOperatorValue,
        value: filterValue
      };
    });
  }

  protected onClear() {
    this.form?.reset();
    this.queryService.deleteFromStorage(this.tableName);
    this.searchService.emitClearEvent();
  }

  private createTreeValue(value: any, attr: Attribute) {
    if (attr.searchField?._multiSelect) {
      return value.map((e: any) => {
        return {
          label: AppUtils.getDisplayValue(e, attr.type),
          data: e,
        }
      });
    }
    return {
      label: AppUtils.getDisplayValue(value, attr.type),
      data: value
    }
  }

  private handleForeignKey(attribute: Attribute, filterValue: any): SearchFilter | {} {
    if (attribute.searchField?.isTreeSelect() && filterValue) {
      if (attribute.searchField?._multiSelect) {
        filterValue = filterValue.map((e: { data: any; }) => e.data);
      } else {
        filterValue = filterValue.data;
      }
    }
    let fkSearchField = AppUtils.getFkSearchAttribute(attribute.type);
    if (filterValue && filterValue.length > 0 && fkSearchField.length > 0) {
      return this.createArrayValueSearchFilter(fkSearchField, filterValue, attribute);
    }
    return {
      key: attribute.attr_name,
      searchOperator: SearchOperator.EQUAL,
      value: filterValue
    }
  }

  private getSearchOperator(attribute: Attribute, filterValue: any): SearchOperator {
    if (filterValue === null) return SearchOperator.LIKE_IGNORE_CASE; // Default operator if no filter value

    if (attribute.searchField?.isRanged())
      return SearchOperator.BETWEEN;

    switch (attribute.type) {
      case 'number':
        return SearchOperator.EQUAL
      case 'date':
      case 'boolean':
        return SearchOperator.EQUAL;
      case 'datetime-local':
        return SearchOperator.BETWEEN;
      case 'enum':
        return filterValue.length === 0 ? SearchOperator.LIKE_IGNORE_CASE : SearchOperator.EQUAL;
      default:
        return SearchOperator.LIKE_IGNORE_CASE;
    }
  }

  private transformFilterValue(attribute: Attribute, filterValue: any, searchOperator: SearchOperator): any {
    if (searchOperator === SearchOperator.BETWEEN && attribute.type === 'datetime-local' && filterValue?.length === 2) {
      if (!filterValue[0] || !filterValue[1]) {
        this.toastService.showError(this.translationService.translate("choose_date_range_error"));
        throw new Error("choose_date_range_error");
      }
      const dateFrom = AppDate.from(filterValue[0]).toStartOfDay().toString();
      const dateTo = AppDate.from(filterValue[1]).toEndOfDay().toString();
      return [dateFrom, dateTo];
    }
    return filterValue;
  }

  private createArrayValueSearchFilter(fkSearchField: string[], fkSearchValue: any, attribute: Attribute): SearchFilter {
    let values: {}[] = [];
    if (attribute.searchField?._multiSelect && AppUtils.isForeignKey(attribute.type)) {
      return {
        key: attribute.attr_name,
        searchOperator: SearchOperator.IN,
        value: fkSearchValue
      };
    }
    for (let key of fkSearchField) {
      values.push({
        key: key,
        searchOperator: SearchOperator.IN,
        value: fkSearchValue
      });
    }
    return {
      key: attribute.attr_name,
      searchOperator: SearchOperator.LIKE_IGNORE_CASE,
      value: {
        logicalOperator: LogicalOperator.OR,
        filters: values
      }
    };
  }
}
