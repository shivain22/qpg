import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { QuestionTypeCategoryMasterComponent } from './question-type-category-master.component';
import { QuestionTypeCategoryMasterDetailComponent } from './question-type-category-master-detail.component';
import { QuestionTypeCategoryMasterUpdateComponent } from './question-type-category-master-update.component';
import { QuestionTypeCategoryMasterDeleteDialogComponent } from './question-type-category-master-delete-dialog.component';
import { questionTypeCategoryMasterRoute } from './question-type-category-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(questionTypeCategoryMasterRoute)],
  declarations: [
    QuestionTypeCategoryMasterComponent,
    QuestionTypeCategoryMasterDetailComponent,
    QuestionTypeCategoryMasterUpdateComponent,
    QuestionTypeCategoryMasterDeleteDialogComponent,
  ],
  entryComponents: [QuestionTypeCategoryMasterDeleteDialogComponent],
})
export class QpgQuestionTypeCategoryMasterModule {}
