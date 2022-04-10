import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { CategoryMasterComponent } from './category-master.component';
import { CategoryMasterDetailComponent } from './category-master-detail.component';
import { CategoryMasterUpdateComponent } from './category-master-update.component';
import { CategoryMasterDeleteDialogComponent } from './category-master-delete-dialog.component';
import { categoryMasterRoute } from './category-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(categoryMasterRoute)],
  declarations: [
    CategoryMasterComponent,
    CategoryMasterDetailComponent,
    CategoryMasterUpdateComponent,
    CategoryMasterDeleteDialogComponent,
  ],
  entryComponents: [CategoryMasterDeleteDialogComponent],
})
export class QpgCategoryMasterModule {}
