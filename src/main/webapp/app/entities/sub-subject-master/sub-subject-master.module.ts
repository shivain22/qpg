import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { SubSubjectMasterComponent } from './sub-subject-master.component';
import { SubSubjectMasterDetailComponent } from './sub-subject-master-detail.component';
import { SubSubjectMasterUpdateComponent } from './sub-subject-master-update.component';
import { SubSubjectMasterDeleteDialogComponent } from './sub-subject-master-delete-dialog.component';
import { subSubjectMasterRoute } from './sub-subject-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(subSubjectMasterRoute)],
  declarations: [
    SubSubjectMasterComponent,
    SubSubjectMasterDetailComponent,
    SubSubjectMasterUpdateComponent,
    SubSubjectMasterDeleteDialogComponent,
  ],
  entryComponents: [SubSubjectMasterDeleteDialogComponent],
})
export class QpgSubSubjectMasterModule {}
