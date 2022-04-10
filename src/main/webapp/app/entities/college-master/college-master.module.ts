import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { CollegeMasterComponent } from './college-master.component';
import { CollegeMasterDetailComponent } from './college-master-detail.component';
import { CollegeMasterUpdateComponent } from './college-master-update.component';
import { CollegeMasterDeleteDialogComponent } from './college-master-delete-dialog.component';
import { collegeMasterRoute } from './college-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(collegeMasterRoute)],
  declarations: [CollegeMasterComponent, CollegeMasterDetailComponent, CollegeMasterUpdateComponent, CollegeMasterDeleteDialogComponent],
  entryComponents: [CollegeMasterDeleteDialogComponent],
})
export class QpgCollegeMasterModule {}
