import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDifficultyTypeMaster, DifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';
import { DifficultyTypeMasterService } from './difficulty-type-master.service';

@Component({
  selector: 'jhi-difficulty-type-master-update',
  templateUrl: './difficulty-type-master-update.component.html',
})
export class DifficultyTypeMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
  });

  constructor(
    protected difficultyTypeMasterService: DifficultyTypeMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ difficultyTypeMaster }) => {
      this.updateForm(difficultyTypeMaster);
    });
  }

  updateForm(difficultyTypeMaster: IDifficultyTypeMaster): void {
    this.editForm.patchValue({
      id: difficultyTypeMaster.id,
      name: difficultyTypeMaster.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const difficultyTypeMaster = this.createFromForm();
    if (difficultyTypeMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.difficultyTypeMasterService.update(difficultyTypeMaster));
    } else {
      this.subscribeToSaveResponse(this.difficultyTypeMasterService.create(difficultyTypeMaster));
    }
  }

  private createFromForm(): IDifficultyTypeMaster {
    return {
      ...new DifficultyTypeMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDifficultyTypeMaster>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
