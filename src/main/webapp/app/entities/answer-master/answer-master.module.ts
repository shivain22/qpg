import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { AnswerMasterComponent } from './answer-master.component';
import { AnswerMasterDetailComponent } from './answer-master-detail.component';
import { AnswerMasterUpdateComponent } from './answer-master-update.component';
import { AnswerMasterDeleteDialogComponent } from './answer-master-delete-dialog.component';
import { answerMasterRoute } from './answer-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(answerMasterRoute)],
  declarations: [AnswerMasterComponent, AnswerMasterDetailComponent, AnswerMasterUpdateComponent, AnswerMasterDeleteDialogComponent],
  entryComponents: [AnswerMasterDeleteDialogComponent],
})
export class QpgAnswerMasterModule {}
