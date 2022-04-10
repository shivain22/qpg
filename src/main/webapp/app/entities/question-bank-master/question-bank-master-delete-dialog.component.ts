import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionBankMaster } from 'app/shared/model/question-bank-master.model';
import { QuestionBankMasterService } from './question-bank-master.service';

@Component({
  templateUrl: './question-bank-master-delete-dialog.component.html',
})
export class QuestionBankMasterDeleteDialogComponent {
  questionBankMaster?: IQuestionBankMaster;

  constructor(
    protected questionBankMasterService: QuestionBankMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionBankMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionBankMasterListModification');
      this.activeModal.close();
    });
  }
}
