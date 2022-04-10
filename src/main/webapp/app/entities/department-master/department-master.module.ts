import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { DepartmentMasterComponent } from './department-master.component';
import { DepartmentMasterDetailComponent } from './department-master-detail.component';
import { DepartmentMasterUpdateComponent } from './department-master-update.component';
import { DepartmentMasterDeleteDialogComponent } from './department-master-delete-dialog.component';
import { departmentMasterRoute } from './department-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(departmentMasterRoute)],
  declarations: [
    DepartmentMasterComponent,
    DepartmentMasterDetailComponent,
    DepartmentMasterUpdateComponent,
    DepartmentMasterDeleteDialogComponent,
  ],
  entryComponents: [DepartmentMasterDeleteDialogComponent],
})
export class QpgDepartmentMasterModule {}
