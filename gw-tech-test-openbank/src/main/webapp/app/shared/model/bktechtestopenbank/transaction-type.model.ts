export interface ITransactionType {
  id?: number;
  nameTransactionType?: string;
}

export class TransactionType implements ITransactionType {
  constructor(public id?: number, public nameTransactionType?: string) {}
}
