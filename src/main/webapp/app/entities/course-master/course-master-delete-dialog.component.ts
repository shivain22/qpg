import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourseMaster } from 'app/shared/model/course-master.model';
import { CourseMasterService } from './course-master.service';

@Component({
  templateUrl: './course-master-delete-dialog.component.html',
})
export class CourseMasterDeleteDialogComponent {
  courseMaster?: ICourseMaster;

  constructor(
    protected courseMasterService: CourseMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courseMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('courseMasterListModification');
      this.activeModal.close();
    });
  }
}
