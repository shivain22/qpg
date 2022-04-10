import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';
import { SubTopicMasterService } from './sub-topic-master.service';

@Component({
  templateUrl: './sub-topic-master-delete-dialog.component.html',
})
export class SubTopicMasterDeleteDialogComponent {
  subTopicMaster?: ISubTopicMaster;

  constructor(
    protected subTopicMasterService: SubTopicMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subTopicMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subTopicMasterListModification');
      this.activeModal.close();
    });
  }
}
