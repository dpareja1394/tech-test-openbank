import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITransactionType, TransactionType } from 'app/shared/model/bktechtestopenbank/transaction-type.model';
import { TransactionTypeService } from './transaction-type.service';

@Component({
  selector: 'jhi-transaction-type-update',
  templateUrl: './transaction-type-update.component.html',
})
export class TransactionTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nameTransactionType: [null, [Validators.required]],
  });

  constructor(
    protected transactionTypeService: TransactionTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionType }) => {
      this.updateForm(transactionType);
    });
  }

  updateForm(transactionType: ITransactionType): void {
    this.editForm.patchValue({
      id: transactionType.id,
      nameTransactionType: transactionType.nameTransactionType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionType = this.createFromForm();
    if (transactionType.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionTypeService.update(transactionType));
    } else {
      this.subscribeToSaveResponse(this.transactionTypeService.create(transactionType));
    }
  }

  private createFromForm(): ITransactionType {
    return {
      ...new TransactionType(),
      id: this.editForm.get(['id'])!.value,
      nameTransactionType: this.editForm.get(['nameTransactionType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionType>>): void {
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
}
