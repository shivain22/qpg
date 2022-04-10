import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { QuestionMasterComponent } from './question-master.component';
import { QuestionMasterDetailComponent } from './question-master-detail.component';
import { QuestionMasterUpdateComponent } from './question-master-update.component';
import { QuestionMasterDeleteDialogComponent } from './question-master-delete-dialog.component';
import { questionMasterRoute } from './question-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(questionMasterRoute)],
  declarations: [
    QuestionMasterComponent,
    QuestionMasterDetailComponent,
    QuestionMasterUpdateComponent,
    QuestionMasterDeleteDialogComponent,
  ],
  entryComponents: [QuestionMasterDeleteDialogComponent],
})
export class QpgQuestionMasterModule {}
