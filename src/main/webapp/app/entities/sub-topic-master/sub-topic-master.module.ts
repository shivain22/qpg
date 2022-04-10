import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { SubTopicMasterComponent } from './sub-topic-master.component';
import { SubTopicMasterDetailComponent } from './sub-topic-master-detail.component';
import { SubTopicMasterUpdateComponent } from './sub-topic-master-update.component';
import { SubTopicMasterDeleteDialogComponent } from './sub-topic-master-delete-dialog.component';
import { subTopicMasterRoute } from './sub-topic-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(subTopicMasterRoute)],
  declarations: [
    SubTopicMasterComponent,
    SubTopicMasterDetailComponent,
    SubTopicMasterUpdateComponent,
    SubTopicMasterDeleteDialogComponent,
  ],
  entryComponents: [SubTopicMasterDeleteDialogComponent],
})
export class QpgSubTopicMasterModule {}
