import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';
import { DifficultyTypeMasterService } from './difficulty-type-master.service';

@Component({
  templateUrl: './difficulty-type-master-delete-dialog.component.html',
})
export class DifficultyTypeMasterDeleteDialogComponent {
  difficultyTypeMaster?: IDifficultyTypeMaster;

  constructor(
    protected difficultyTypeMasterService: DifficultyTypeMasterService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.difficultyTypeMasterService.delete(id).subscribe(() => {
      this.eventManager.broadcast('difficultyTypeMasterListModification');
      this.activeModal.close();
    });
  }
}
