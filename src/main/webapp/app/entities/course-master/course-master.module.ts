import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { CourseMasterComponent } from './course-master.component';
import { CourseMasterDetailComponent } from './course-master-detail.component';
import { CourseMasterUpdateComponent } from './course-master-update.component';
import { CourseMasterDeleteDialogComponent } from './course-master-delete-dialog.component';
import { courseMasterRoute } from './course-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(courseMasterRoute)],
  declarations: [CourseMasterComponent, CourseMasterDetailComponent, CourseMasterUpdateComponent, CourseMasterDeleteDialogComponent],
  entryComponents: [CourseMasterDeleteDialogComponent],
})
export class QpgCourseMasterModule {}
