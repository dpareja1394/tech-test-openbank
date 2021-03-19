import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransaction, Transaction } from 'app/shared/model/bktechtestopenbank/transaction.model';
import { TransactionService } from './transaction.service';
import { ITransactionType } from 'app/shared/model/bktechtestopenbank/transaction-type.model';
import { TransactionTypeService } from 'app/entities/bktechtestopenbank/transaction-type/transaction-type.service';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html',
})
export class TransactionUpdateComponent implements OnInit {
  isSaving = false;
  transactiontypes: ITransactionType[] = [];

  editForm = this.fb.group({
    id: [],
    accountId: [null, [Validators.required]],
    counterPartyAccount: [null, [Validators.required]],
    counterPartyName: [null, [Validators.required]],
    counterPartyLogoPath: [null, [Validators.required]],
    instructedAmount: [null, [Validators.required]],
    instructedCurrency: [null, [Validators.required]],
    transactionAmount: [null, [Validators.required]],
    transactionCurrency: [null, [Validators.required]],
    description: [null, [Validators.required]],
    transactionTypeId: [null, Validators.required],
  });

  constructor(
    protected transactionService: TransactionService,
    protected transactionTypeService: TransactionTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);

      this.transactionTypeService.query().subscribe((res: HttpResponse<ITransactionType[]>) => (this.transactiontypes = res.body || []));
    });
  }

  updateForm(transaction: ITransaction): void {
    this.editForm.patchValue({
      id: transaction.id,
      accountId: transaction.accountId,
      counterPartyAccount: transaction.counterPartyAccount,
      counterPartyName: transaction.counterPartyName,
      counterPartyLogoPath: transaction.counterPartyLogoPath,
      instructedAmount: transaction.instructedAmount,
      instructedCurrency: transaction.instructedCurrency,
      transactionAmount: transaction.transactionAmount,
      transactionCurrency: transaction.transactionCurrency,
      description: transaction.description,
      transactionTypeId: transaction.transactionTypeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id'])!.value,
      accountId: this.editForm.get(['accountId'])!.value,
      counterPartyAccount: this.editForm.get(['counterPartyAccount'])!.value,
      counterPartyName: this.editForm.get(['counterPartyName'])!.value,
      counterPartyLogoPath: this.editForm.get(['counterPartyLogoPath'])!.value,
      instructedAmount: this.editForm.get(['instructedAmount'])!.value,
      instructedCurrency: this.editForm.get(['instructedCurrency'])!.value,
      transactionAmount: this.editForm.get(['transactionAmount'])!.value,
      transactionCurrency: this.editForm.get(['transactionCurrency'])!.value,
      description: this.editForm.get(['description'])!.value,
      transactionTypeId: this.editForm.get(['transactionTypeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITransactionType): any {
    return item.id;
  }
}
