import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';

import { questionChoiceMasterRoute } from './question-choice-master.route';
import { QuestionChoiceMasterComponent } from './question-choice-master.component';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(questionChoiceMasterRoute)],
  declarations: [QuestionChoiceMasterComponent],
})
export class QpgQuestionChoiceMasterModule {}
