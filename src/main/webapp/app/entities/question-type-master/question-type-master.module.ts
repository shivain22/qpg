import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { QuestionTypeMasterComponent } from './question-type-master.component';
import { QuestionTypeMasterDetailComponent } from './question-type-master-detail.component';
import { QuestionTypeMasterUpdateComponent } from './question-type-master-update.component';
import { QuestionTypeMasterDeleteDialogComponent } from './question-type-master-delete-dialog.component';
import { questionTypeMasterRoute } from './question-type-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(questionTypeMasterRoute)],
  declarations: [
    QuestionTypeMasterComponent,
    QuestionTypeMasterDetailComponent,
    QuestionTypeMasterUpdateComponent,
    QuestionTypeMasterDeleteDialogComponent,
  ],
  entryComponents: [QuestionTypeMasterDeleteDialogComponent],
})
export class QpgQuestionTypeMasterModule {}
