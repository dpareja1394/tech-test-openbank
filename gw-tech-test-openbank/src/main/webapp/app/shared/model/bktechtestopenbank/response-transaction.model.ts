import { Transaction } from './transaction.model';

export interface IResponseTransaction {
  transactionsFilterByTransactionType?: Transaction[];
  totalAmmountForTransactionType?: number;
}

export class ResponseTransaction implements IResponseTransaction {
  constructor(public transactionsFilterByTransactionType?: Transaction[], public totalAmmountForTransactionType?: number) {}
}
