import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { QbMasterComponent } from './qb-master.component';
import { QbMasterDetailComponent } from './qb-master-detail.component';
import { QbMasterUpdateComponent } from './qb-master-update.component';
import { QbMasterDeleteDialogComponent } from './qb-master-delete-dialog.component';
import { qbMasterRoute } from './qb-master.route';


@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(qbMasterRoute)],
  declarations: [QbMasterComponent, QbMasterDetailComponent, QbMasterUpdateComponent, QbMasterDeleteDialogComponent],
  entryComponents: [QbMasterDeleteDialogComponent],
})

export class QpgQbMasterModule {}
