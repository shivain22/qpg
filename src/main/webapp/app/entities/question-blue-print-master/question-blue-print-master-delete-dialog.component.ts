import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';
import { QuestionBluePrintMasterService } from './question-blue-print-master.service';

@Component({
  templateUrl: './question-blue-print-master-delete-dialog.component.html',
})
export class QuestionBluePrintMasterDeleteDialogComponent {
  questionBluePrintMaster?: IQuestionBluePrintMaster;

  constructor(
    protected questionBluePrintMasterService: QuestionBluePrintMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionBluePrintMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionBluePrintMasterListModification');
      this.activeModal.close();
    });
  }
}
