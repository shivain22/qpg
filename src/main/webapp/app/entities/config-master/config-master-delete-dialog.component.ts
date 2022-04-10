import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfigMaster } from 'app/shared/model/config-master.model';
import { ConfigMasterService } from './config-master.service';

@Component({
  templateUrl: './config-master-delete-dialog.component.html',
})
export class ConfigMasterDeleteDialogComponent {
  configMaster?: IConfigMaster;

  constructor(
    protected configMasterService: ConfigMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.configMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('configMasterListModification');
      this.activeModal.close();
    });
  }
}
