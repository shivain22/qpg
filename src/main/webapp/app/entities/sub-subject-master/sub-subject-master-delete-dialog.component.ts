import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubSubjectMaster } from 'app/shared/model/sub-subject-master.model';
import { SubSubjectMasterService } from './sub-subject-master.service';

@Component({
  templateUrl: './sub-subject-master-delete-dialog.component.html',
})
export class SubSubjectMasterDeleteDialogComponent {
  subSubjectMaster?: ISubSubjectMaster;

  constructor(
    protected subSubjectMasterService: SubSubjectMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subSubjectMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subSubjectMasterListModification');
      this.activeModal.close();
    });
  }
}
