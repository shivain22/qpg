import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionMaster } from 'app/shared/model/question-master.model';
import { QuestionMasterService } from './question-master.service';

@Component({
  templateUrl: './question-master-delete-dialog.component.html',
})
export class QuestionMasterDeleteDialogComponent {
  questionMaster?: IQuestionMaster;

  constructor(
    protected questionMasterService: QuestionMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionMasterListModification');
      this.activeModal.close();
    });
  }
}
