import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransaction } from 'app/shared/model/bktechtestopenbank/transaction.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TransactionService } from './transaction.service';
import { TransactionDeleteDialogComponent } from './transaction-delete-dialog.component';
import { ITransactionType } from 'app/shared/model/bktechtestopenbank/transaction-type.model';
import { TransactionTypeService } from '../transaction-type/transaction-type.service';

@Component({
  selector: 'jhi-transaction',
  templateUrl: './transaction.component.html',
})
export class TransactionComponent implements OnInit, OnDestroy {
  transactions?: ITransaction[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  transactionsFilterByTransactionType?: ITransaction[];
  transactionTypes?: ITransactionType[];
  selectedTransactionTypeId = 0;
  totalAmmount = 0;

  constructor(
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected transactionTypeService: TransactionTypeService
  ) {}

  loadPage(): void {
    this.transactionService.findAll().subscribe(
      (res: HttpResponse<ITransaction[]>) => (this.transactions = res.body || []),
      () => this.onError()
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInTransactions();
    this.transactionTypeService.findAll().subscribe((res: HttpResponse<ITransactionType[]>) => (this.transactionTypes = res.body || []));
    this.transactionService
      .totalAmountByTransactionType(this.selectedTransactionTypeId)
      .toPromise()
      .then(res => {
        this.totalAmmount = res.body || 0;
      });
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage();
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransaction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransactions(): void {
    this.eventSubscriber = this.eventManager.subscribe('transactionListModification', () => this.loadPage());
  }

  delete(transaction: ITransaction): void {
    const modalRef = this.modalService.open(TransactionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transaction = transaction;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ITransaction[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/transaction'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.transactions = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  filterByTransactionType(): void {
    // eslint-disable-next-line no-console
    console.log('Change filter to ', this.selectedTransactionTypeId);
    if (this.selectedTransactionTypeId === 0) {
      this.transactionService
        .findAll()
        .toPromise()
        .then(res => {
          this.transactions = res.body || [];
        });
    } else {
      this.transactionService
        .findTransactionsByTransactionType(this.selectedTransactionTypeId)
        .toPromise()
        .then(res => {
          this.transactions = res.body || [];
        });
    }

    this.transactionService
      .totalAmountByTransactionType(this.selectedTransactionTypeId)
      .toPromise()
      .then(res => {
        this.totalAmmount = res.body || 0;
      });
  }
}
