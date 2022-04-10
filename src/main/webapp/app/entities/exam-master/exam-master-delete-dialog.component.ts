import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamMaster } from 'app/shared/model/exam-master.model';
import { ExamMasterService } from './exam-master.service';

@Component({
  templateUrl: './exam-master-delete-dialog.component.html',
})
export class ExamMasterDeleteDialogComponent {
  examMaster?: IExamMaster;

  constructor(
    protected examMasterService: ExamMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('examMasterListModification');
      this.activeModal.close();
    });
  }
}
