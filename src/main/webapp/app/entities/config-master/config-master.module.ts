import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QpgSharedModule } from 'app/shared/shared.module';
import { ConfigMasterComponent } from './config-master.component';
import { ConfigMasterDetailComponent } from './config-master-detail.component';
import { ConfigMasterUpdateComponent } from './config-master-update.component';
import { ConfigMasterDeleteDialogComponent } from './config-master-delete-dialog.component';
import { configMasterRoute } from './config-master.route';

@NgModule({
  imports: [QpgSharedModule, RouterModule.forChild(configMasterRoute)],
  declarations: [ConfigMasterComponent, ConfigMasterDetailComponent, ConfigMasterUpdateComponent, ConfigMasterDeleteDialogComponent],
  entryComponents: [ConfigMasterDeleteDialogComponent],
})
export class QpgConfigMasterModule {}
