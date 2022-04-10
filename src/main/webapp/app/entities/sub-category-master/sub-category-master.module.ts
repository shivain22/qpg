import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { SubCategoryMasterComponent } from './sub-category-master.component';
import { SubCategoryMasterDetailComponent } from './sub-category-master-detail.component';
import { SubCategoryMasterUpdateComponent } from './sub-category-master-update.component';
import { SubCategoryMasterDeleteDialogComponent } from './sub-category-master-delete-dialog.component';
import { subCategoryMasterRoute } from './sub-category-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(subCategoryMasterRoute)],
  declarations: [
    SubCategoryMasterComponent,
    SubCategoryMasterDetailComponent,
    SubCategoryMasterUpdateComponent,
    SubCategoryMasterDeleteDialogComponent,
  ],
  entryComponents: [SubCategoryMasterDeleteDialogComponent],
})
export class QpgSubCategoryMasterModule {}
