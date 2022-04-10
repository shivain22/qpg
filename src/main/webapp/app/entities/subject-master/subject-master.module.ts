import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { SubjectMasterComponent } from './subject-master.component';
import { SubjectMasterDetailComponent } from './subject-master-detail.component';
import { SubjectMasterUpdateComponent } from './subject-master-update.component';
import { SubjectMasterDeleteDialogComponent } from './subject-master-delete-dialog.component';
import { subjectMasterRoute } from './subject-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(subjectMasterRoute)],
  declarations: [SubjectMasterComponent, SubjectMasterDetailComponent, SubjectMasterUpdateComponent, SubjectMasterDeleteDialogComponent],
  entryComponents: [SubjectMasterDeleteDialogComponent],
})
export class QpgSubjectMasterModule {}
