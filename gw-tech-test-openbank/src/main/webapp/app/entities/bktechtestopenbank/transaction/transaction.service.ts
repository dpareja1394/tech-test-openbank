import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransaction } from 'app/shared/model/bktechtestopenbank/transaction.model';

type EntityResponseType = HttpResponse<ITransaction>;
type EntityArrayResponseType = HttpResponse<ITransaction[]>;

@Injectable({ providedIn: 'root' })
export class TransactionService {
  public resourceUrl = SERVER_API_URL + 'services/bktechtestopenbank/api/transactions';

  constructor(protected http: HttpClient) {}

  create(transaction: ITransaction): Observable<EntityResponseType> {
    return this.http.post<ITransaction>(this.resourceUrl, transaction, { observe: 'response' });
  }

  update(transaction: ITransaction): Observable<EntityResponseType> {
    return this.http.put<ITransaction>(this.resourceUrl, transaction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<ITransaction[]>(`${this.resourceUrl}/findAll`, { observe: 'response' });
  }

  findTransactionsByTransactionType(transactionTypeId: number): Observable<EntityArrayResponseType> {
    return this.http.get<ITransaction[]>(`${this.resourceUrl}/findTransactionsByTransactionType/${transactionTypeId}`, {
      observe: 'response',
    });
  }

  totalAmountByTransactionType(transactionTypeId: number): Observable<HttpResponse<number>> {
    return this.http.get<number>(`${this.resourceUrl}/totalAmountByTransactionType/${transactionTypeId}`, { observe: 'response' });
  }
}
