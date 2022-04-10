import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { QuestionBluePrintMasterComponent } from './question-blue-print-master.component';
import { QuestionBluePrintMasterDetailComponent } from './question-blue-print-master-detail.component';
import { QuestionBluePrintMasterUpdateComponent } from './question-blue-print-master-update.component';
import { QuestionBluePrintMasterDeleteDialogComponent } from './question-blue-print-master-delete-dialog.component';
import { questionBluePrintMasterRoute } from './question-blue-print-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(questionBluePrintMasterRoute)],
  declarations: [
    QuestionBluePrintMasterComponent,
    QuestionBluePrintMasterDetailComponent,
    QuestionBluePrintMasterUpdateComponent,
    QuestionBluePrintMasterDeleteDialogComponent,
  ],
  entryComponents: [QuestionBluePrintMasterDeleteDialogComponent],
})
export class QpgQuestionBluePrintMasterModule {}
