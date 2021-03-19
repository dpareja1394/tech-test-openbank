export interface ITransaction {
  id?: number;
  accountId?: number;
  counterPartyAccount?: number;
  counterPartyName?: string;
  counterPartyLogoPath?: string;
  instructedAmount?: number;
  instructedCurrency?: string;
  transactionAmount?: number;
  transactionCurrency?: string;
  description?: string;
  transactionTypeId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public accountId?: number,
    public counterPartyAccount?: number,
    public counterPartyName?: string,
    public counterPartyLogoPath?: string,
    public instructedAmount?: number,
    public instructedCurrency?: string,
    public transactionAmount?: number,
    public transactionCurrency?: string,
    public description?: string,
    public transactionTypeId?: number
  ) {}
}
