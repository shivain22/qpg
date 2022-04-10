import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { ExamMasterComponent } from './exam-master.component';
import { ExamMasterDetailComponent } from './exam-master-detail.component';
import { ExamMasterUpdateComponent } from './exam-master-update.component';
import { ExamMasterDeleteDialogComponent } from './exam-master-delete-dialog.component';
import { examMasterRoute } from './exam-master.route';
import {ExamMasterCreateQuestionPaperDetailComponent} from "./exam-master-create-question-paper-detail.component";

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(examMasterRoute)],
  declarations: [ExamMasterComponent, ExamMasterDetailComponent, ExamMasterUpdateComponent, ExamMasterDeleteDialogComponent, ExamMasterCreateQuestionPaperDetailComponent],
  entryComponents: [ExamMasterDeleteDialogComponent],
})
export class QpgExamMasterModule {}
