import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'transaction',
        loadChildren: () => import('./bktechtestopenbank/transaction/transaction.module').then(m => m.BktechtestopenbankTransactionModule),
      },
      {
        path: 'transaction-type',
        loadChildren: () =>
          import('./bktechtestopenbank/transaction-type/transaction-type.module').then(m => m.BktechtestopenbankTransactionTypeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GwtechtestopenbankEntityModule {}
