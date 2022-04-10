import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnswerMaster } from 'app/shared/model/answer-master.model';
import { AnswerMasterService } from './answer-master.service';

@Component({
  templateUrl: './answer-master-delete-dialog.component.html',
})
export class AnswerMasterDeleteDialogComponent {
  answerMaster?: IAnswerMaster;

  constructor(
    protected answerMasterService: AnswerMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.answerMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('answerMasterListModification');
      this.activeModal.close();
    });
  }
}
