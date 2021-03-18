import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { GwtechtestopenbankSharedModule } from 'app/shared/shared.module';

import { MetricsComponent } from './metrics.component';

import { metricsRoute } from './metrics.route';

@NgModule({
  imports: [GwtechtestopenbankSharedModule, RouterModule.forChild([metricsRoute])],
  declarations: [MetricsComponent],
})
export class MetricsModule {}
