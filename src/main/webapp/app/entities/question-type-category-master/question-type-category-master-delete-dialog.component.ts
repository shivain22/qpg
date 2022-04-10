import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionTypeMaster } from 'app/shared/model/question-type-master.model';
import { QuestionTypeCategoryMasterService } from './question-type-category-master.service';

@Component({
  templateUrl: './question-type-category-master-delete-dialog.component.html',
})
export class QuestionTypeCategoryMasterDeleteDialogComponent {
  questionTypeMaster?: IQuestionTypeMaster;

  constructor(
    protected questionTypeMasterService: QuestionTypeCategoryMasterService,
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
