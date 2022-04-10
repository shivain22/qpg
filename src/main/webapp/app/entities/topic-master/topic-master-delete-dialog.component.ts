import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITopicMaster } from 'app/shared/model/topic-master.model';
import { TopicMasterService } from './topic-master.service';

@Component({
  templateUrl: './topic-master-delete-dialog.component.html',
})
export class TopicMasterDeleteDialogComponent {
  topicMaster?: ITopicMaster;

  constructor(
    protected topicMasterService: TopicMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.topicMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('topicMasterListModification');
      this.activeModal.close();
    });
  }
}
