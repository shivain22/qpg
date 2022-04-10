import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { TopicMasterComponent } from './topic-master.component';
import { TopicMasterDetailComponent } from './topic-master-detail.component';
import { TopicMasterUpdateComponent } from './topic-master-update.component';
import { TopicMasterDeleteDialogComponent } from './topic-master-delete-dialog.component';
import { topicMasterRoute } from './topic-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(topicMasterRoute)],
  declarations: [TopicMasterComponent, TopicMasterDetailComponent, TopicMasterUpdateComponent, TopicMasterDeleteDialogComponent],
  entryComponents: [TopicMasterDeleteDialogComponent],
})
export class QpgTopicMasterModule {}
