import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { DifficultyTypeMasterComponent } from './difficulty-type-master.component';
import { DifficultyTypeMasterDetailComponent } from './difficulty-type-master-detail.component';
import { DifficultyTypeMasterUpdateComponent } from './difficulty-type-master-update.component';
import { DifficultyTypeMasterDeleteDialogComponent } from './difficulty-type-master-delete-dialog.component';
import { difficultyTypeMasterRoute } from './difficulty-type-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(difficultyTypeMasterRoute)],
  declarations: [
    DifficultyTypeMasterComponent,
    DifficultyTypeMasterDetailComponent,
    DifficultyTypeMasterUpdateComponent,
    DifficultyTypeMasterDeleteDialogComponent,
  ],
  entryComponents: [DifficultyTypeMasterDeleteDialogComponent],
})
export class QpgDifficultyTypeMasterModule {}
