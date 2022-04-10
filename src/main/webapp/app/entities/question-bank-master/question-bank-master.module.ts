import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { QuestionBankMasterComponent } from './question-bank-master.component';
import { QuestionBankMasterDetailComponent } from './question-bank-master-detail.component';
import { QuestionBankMasterUpdateComponent } from './question-bank-master-update.component';
import { QuestionBankMasterDeleteDialogComponent } from './question-bank-master-delete-dialog.component';
import { questionBankMasterRoute } from './question-bank-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(questionBankMasterRoute)],
  declarations: [
    QuestionBankMasterComponent,
    QuestionBankMasterDetailComponent,
    QuestionBankMasterUpdateComponent,
    QuestionBankMasterDeleteDialogComponent,
  ],
  entryComponents: [QuestionBankMasterDeleteDialogComponent],
})
export class QpgQuestionBankMasterModule {}
