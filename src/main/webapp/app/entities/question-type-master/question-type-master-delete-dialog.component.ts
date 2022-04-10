import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionTypeMaster } from 'app/shared/model/question-type-master.model';
import { QuestionTypeMasterService } from './question-type-master.service';

@Component({
  templateUrl: './question-type-master-delete-dialog.component.html',
})
export class QuestionTypeMasterDeleteDialogComponent {
  questionTypeMaster?: IQuestionTypeMaster;

  constructor(
    protected questionTypeMasterService: QuestionTypeMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionTypeMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionTypeMasterListModification');
      this.activeModal.close();
    });
  }
}
