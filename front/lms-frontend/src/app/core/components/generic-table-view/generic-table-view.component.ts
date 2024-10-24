import {AfterViewInit, ChangeDetectorRef, Component, ElementRef, inject, Input, OnInit, ViewChild} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {RootComponent} from "../root/root.component";
import {Attribute} from "../../entity-utils/attribute";
import {Page} from "../../entity-utils/page";
import {ApiService} from "../../services/api.service";
import {Order} from "../../entity-utils/order";
import {AppUtils} from "../../../shared/utils/app-utils";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {SearchService} from "../../services/search.service";
import {SearchDTO} from "../../entity-utils/search-dto";
import {FormsModule} from '@angular/forms';
import {PrimeModule} from '../../../shared/prime/prime.modules';
import {Observable, take} from "rxjs";
import {Structure} from "../../../features/entities/structure";
import {ConfirmationService, SortEvent} from 'primeng/api';
import {ToastService} from '../../services/toast.service';
import {TranslationService} from '../../services/translation.service';
import {PermissionService} from '../../services/permission.service';
import {QueryService} from "../../services/query.service";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {OrderDirection} from "../../entity-utils/order-direction";

@Component({
  selector: 'app-generic-table-view',
  standalone: true,
  imports: [NgForOf, NgIf, RouterLink, RouterLinkActive, PrimeModule, FormsModule, ConfirmDialogModule],
  templateUrl: './generic-table-view.component.html',
  styleUrl: './generic-table-view.component.scss'
})
export class GenericTableViewComponent extends RootComponent implements OnInit, AfterViewInit {
  searchService: SearchService = inject(SearchService);
  override service: ApiService = inject(ApiService);
  cdr: ChangeDetectorRef = inject(ChangeDetectorRef);
  translationService = inject(TranslationService);
  router = inject(Router);
  permissionService = inject(PermissionService);
  queryService = inject(QueryService);
  @Input() disableActions: boolean = false;
  @Input() structure?: Structure;
  @Input() tableAttributes!: Attribute[];

  entity: string = "";
  tableName: string = "";
  attributes: Attribute[] = [];
  page?: Page;
  dataSource: any[] = this.page ? this.page.content : [];
  displayedColumns: string[] = [];
  foreignKeys: Map<string, string> = new Map<string, string>();
  showLoader: boolean = false;
  pageSize: number = 10;

  /**
   * Prime Variables
   */
  loading: boolean = false;
  @ViewChild('filter') filter!: ElementRef;
  first = 0;
  totalRecords = 0;
  rows = (this.page?.size ? this.page?.size : 10);

  /**
   * Prime end
   */
  toastService = inject(ToastService)
  sortField: string | undefined | null;
  sortOrder: number | undefined | null;
  protected readonly AppUtils = AppUtils;
  private confirmationService = inject(ConfirmationService);

  ngOnInit() {
    this.initEntityData();
    this.displayedColumns = this.getDisplayedColumns();
    if (!this.disableActions) this.displayedColumns.push('actions');
    this.attributes.filter(e => AppUtils.isForeignKey(e.type)).forEach(e => this.foreignKeys.set(e.attr_name, e.type));

    this.pageSize = this.findPageSize();

    this.populateWithKnownQuery();
  }

  ngAfterViewInit(): void {
    this.performSubscription(this.searchService.event$, (data: any) => {
      data.subscribe((page: Page | undefined) => {
        this.updateData(page);
      })
    });
    this.performSubscription(this.searchService.loadingEvent$, (loading: any) => {
      this.loading = loading;
    });
    this.performSubscription(this.searchService.clearEvent$, () => {
      this.dataSource = []
    });
  }

  onSortChange(event: SortEvent) {
    let searchDto = this.queryService.getFromStorage(this.tableName);
    if (!searchDto || !event.field) return;

    searchDto = this.prepareSortChange(event, searchDto);

    this.queryService.saveToStorage(this.tableName, searchDto);

      searchDto.pageSize = this.findPageSize();
      this.tableChangeSearch(searchDto, this.entity);
  }

  transformContent(dataSource: any[]) {
    let data = dataSource;
    for (let object of data) {
      this.foreignKeys.forEach((type, attr_name) => {
        if (object[attr_name]) {
          object[attr_name] = AppUtils.getEntityViewValue(object[attr_name], type);
        }
      });
    }
    return data;
  }

  getDisplayedColumns() {
    return this.attributes.map(e => e.attr_name);
  }

  onPageChange(event: any): void {
    this.pageSize = event.rows;
    this.first = event.first;

    let dto = this.queryService.getFromStorage(this.tableName);
    if (dto) {
      dto.pageSize = this.pageSize;
      dto.pageNumber = this.first / this.pageSize;
      this.tableChangeSearch(dto, this.entity);
    }
  }

  populateWithKnownQuery() {
    let dto = this.queryService.getFromStorage(this.tableName);
    if (dto) {
      dto.pageSize = 10;
      if (dto.sort && dto.sort.length > 0) {
        // Currently only 1 column is supported.
        this.sortField = dto.sort[0].property;
        let direction = dto.sort[0].direction
        this.sortOrder = direction === OrderDirection.ASC ? 1 : direction === OrderDirection.DESC ? -1 : 0;
      } else {
        this.sortField = ""; // Handle case where dto.sort is empty or undefined
      }
      this.tableChangeSearch(dto, this.entity);
    }
  }


  tableChangeSearch(dto: SearchDTO, entity: string) {
    this.loading = true;
    this.search(dto, entity, (data: Page) => {
      this.updateData(data);
    });
  }

  public findPageSize(): number {
    return Number.parseInt(AppUtils.DEFAULT_PAGE_SIZE);
  }

  onDelete(event: Event, object: any): void {
    const objectDisplayValue = AppUtils.getEntityViewValue(object, this.entity);
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: this.translationService.translate('are_you_sure_delete', objectDisplayValue),
      header: this.translationService.translate('confirmation'),
      icon: 'pi pi-exclamation-triangle text-red-700',
      acceptIcon: "pi pi-save mr-2",
      rejectIcon: "pi pi-times mr-2",
      rejectButtonStyleClass: 'p-button-sm',
      acceptButtonStyleClass: 'p-button-outlined p-button-sm',
      acceptLabel: this.translationService.translate('yes'),
      rejectLabel: this.translationService.translate('no'),
      accept: () => {
        this.delete(object['id'], this.entity, () => {
          this.cdr.detectChanges();
          this.toastService.showInfo(this.translationService.translate('deleted_successfully'));
          this.populateWithKnownQuery();
        });
      },
      reject: () => {
      }
    });

  }

  protected prepareSortChange(event: SortEvent, searchDto: SearchDTO): SearchDTO {
    let attr_type = AppUtils.getAttributeType(event.field ?? '', this.attributes);
    searchDto.sort = [];

    if (attr_type.toLowerCase() === this.entity.toLowerCase()) {
      for (let attr of AppUtils.getFkSearchAttribute(attr_type)) {
        let order = new Order(event.field ?? '', event.order === 1 ? 'asc' : 'desc');
        order.property = attr;
        searchDto.sort.push(order);
      }
    } else {
      let order = new Order(event.field ?? '', event.order === 1 ? 'asc' : 'desc');
      searchDto.sort = [order];
    }

    return searchDto;
  }

  protected updateData(data: Page | undefined) {
    this.page = data;
    this.dataSource = this.page ? [...this.page.content] : [];
    this.dataSource = this.transformContent(this.dataSource);
    this.first = this.page?.number ? this.page.size * this.page.number : 0;
    this.totalRecords = this.page?.totalElements ?? 0;
    this.loading = false;
    this.cdr.detectChanges();
  }

  private initEntityData() {
    this.entity = this.structure?.entityName ?? '';
    this.tableName = this.structure?.title ?? '';
    this.attributes = this.tableAttributes ? this.tableAttributes : this.structure?.attributes || [];
  }
}
