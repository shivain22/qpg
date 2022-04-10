import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoryMaster } from 'app/shared/model/category-master.model';
import { CategoryMasterService } from './category-master.service';

@Component({
  templateUrl: './category-master-delete-dialog.component.html',
})
export class CategoryMasterDeleteDialogComponent {
  categoryMaster?: ICategoryMaster;

  constructor(
    protected categoryMasterService: CategoryMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoryMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoryMasterListModification');
      this.activeModal.close();
    });
  }
}
