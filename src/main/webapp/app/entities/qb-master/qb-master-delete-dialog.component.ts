import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQbMaster } from 'app/shared/model/qb-master.model';
import { QbMasterService } from './qb-master.service';

@Component({
  templateUrl: './qb-master-delete-dialog.component.html',
})
export class QbMasterDeleteDialogComponent {
  qbMaster?: IQbMaster;

  constructor(protected qbMasterService: QbMasterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qbMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('qbMasterListModification');
      this.activeModal.close();
    });
  }
}
