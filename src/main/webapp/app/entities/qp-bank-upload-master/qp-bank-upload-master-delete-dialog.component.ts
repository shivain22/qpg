import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQpBankUploadMaster } from 'app/shared/model/qp-bank-upload-master.model';
import { QpBankUploadMasterService } from './qp-bank-upload-master.service';

@Component({
  templateUrl: './qp-bank-upload-master-delete-dialog.component.html',
})
export class QpBankUploadMasterDeleteDialogComponent {
  qpBankUploadMaster?: IQpBankUploadMaster;

  constructor(
    protected qpBankUploadMasterService: QpBankUploadMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qpBankUploadMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('qpBankUploadMasterListModification');
      this.activeModal.close();
    });
  }
}
