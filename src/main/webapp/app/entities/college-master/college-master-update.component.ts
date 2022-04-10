import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICollegeMaster, CollegeMaster } from 'app/shared/model/college-master.model';
import { CollegeMasterService } from './college-master.service';

@Component({
  selector: 'jhi-college-master-update',
  templateUrl: './college-master-update.component.html',
})
export class CollegeMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
  });

  constructor(protected collegeMasterService: CollegeMasterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collegeMaster }) => {
      this.updateForm(collegeMaster);
    });
  }

  updateForm(collegeMaster: ICollegeMaster): void {
    this.editForm.patchValue({
      id: collegeMaster.id,
      name: collegeMaster.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collegeMaster = this.createFromForm();
    if (collegeMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.collegeMasterService.update(collegeMaster));
    } else {
      this.subscribeToSaveResponse(this.collegeMasterService.create(collegeMaster));
    }
  }

  private createFromForm(): ICollegeMaster {
    return {
      ...new CollegeMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICollegeMaster>>): void {
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
