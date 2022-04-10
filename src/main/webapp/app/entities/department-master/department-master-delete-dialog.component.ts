import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartmentMaster } from 'app/shared/model/department-master.model';
import { DepartmentMasterService } from './department-master.service';

@Component({
  templateUrl: './department-master-delete-dialog.component.html',
})
export class DepartmentMasterDeleteDialogComponent {
  departmentMaster?: IDepartmentMaster;

  constructor(
    protected departmentMasterService: DepartmentMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.departmentMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('departmentMasterListModification');
      this.activeModal.close();
    });
  }
}
