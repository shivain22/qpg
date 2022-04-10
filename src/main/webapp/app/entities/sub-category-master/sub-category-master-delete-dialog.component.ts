import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubCategoryMaster } from 'app/shared/model/sub-category-master.model';
import { SubCategoryMasterService } from './sub-category-master.service';

@Component({
  templateUrl: './sub-category-master-delete-dialog.component.html',
})
export class SubCategoryMasterDeleteDialogComponent {
  subCategoryMaster?: ISubCategoryMaster;

  constructor(
    protected subCategoryMasterService: SubCategoryMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subCategoryMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subCategoryMasterListModification');
      this.activeModal.close();
    });
  }
}
