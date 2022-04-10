import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { TestEntityComponent } from './test-entity.component';
import { TestEntityDetailComponent } from './test-entity-detail.component';
import { TestEntityUpdateComponent } from './test-entity-update.component';
import { TestEntityDeleteDialogComponent } from './test-entity-delete-dialog.component';
import { testEntityRoute } from './test-entity.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(testEntityRoute)],
  declarations: [TestEntityComponent, TestEntityDetailComponent, TestEntityUpdateComponent, TestEntityDeleteDialogComponent],
  entryComponents: [TestEntityDeleteDialogComponent],
})
export class QpgTestEntityModule {}
