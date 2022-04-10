import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { QpBankUploadMasterComponent } from './qp-bank-upload-master.component';
import { QpBankUploadMasterDetailComponent } from './qp-bank-upload-master-detail.component';
import { QpBankUploadMasterUpdateComponent } from './qp-bank-upload-master-update.component';
import { QpBankUploadMasterDeleteDialogComponent } from './qp-bank-upload-master-delete-dialog.component';
import { qpBankUploadMasterRoute } from './qp-bank-upload-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(qpBankUploadMasterRoute)],
  declarations: [
    QpBankUploadMasterComponent,
    QpBankUploadMasterDetailComponent,
    QpBankUploadMasterUpdateComponent,
    QpBankUploadMasterDeleteDialogComponent,
  ],
  entryComponents: [QpBankUploadMasterDeleteDialogComponent],
})
export class QpgQpBankUploadMasterModule {}
