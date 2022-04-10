import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubjectMaster } from 'app/shared/model/subject-master.model';
import { SubjectMasterService } from './subject-master.service';

@Component({
  templateUrl: './subject-master-delete-dialog.component.html',
})
export class SubjectMasterDeleteDialogComponent {
  subjectMaster?: ISubjectMaster;

  constructor(
    protected subjectMasterService: SubjectMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subjectMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subjectMasterListModification');
      this.activeModal.close();
    });
  }
}
