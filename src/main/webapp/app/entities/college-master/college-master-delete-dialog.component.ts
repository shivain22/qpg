import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollegeMaster } from 'app/shared/model/college-master.model';
import { CollegeMasterService } from './college-master.service';

@Component({
  templateUrl: './college-master-delete-dialog.component.html',
})
export class CollegeMasterDeleteDialogComponent {
  collegeMaster?: ICollegeMaster;

  constructor(
    protected collegeMasterService: CollegeMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collegeMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('collegeMasterListModification');
      this.activeModal.close();
    });
  }
}
