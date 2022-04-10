import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubjectMaster, SubjectMaster } from 'app/shared/model/subject-master.model';
import { SubjectMasterService } from './subject-master.service';
import { ISubCategoryMaster } from 'app/shared/model/sub-category-master.model';
import { SubCategoryMasterService } from 'app/entities/sub-category-master/sub-category-master.service';

@Component({
  selector: 'jhi-subject-master-update',
  templateUrl: './subject-master-update.component.html',
})
export class SubjectMasterUpdateComponent implements OnInit {
  isSaving = false;
  subcategorymasters: ISubCategoryMaster[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    subCategoryMaster: [null, Validators.required],
  });

  constructor(
    protected subjectMasterService: SubjectMasterService,
    protected subCategoryMasterService: SubCategoryMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subjectMaster }) => {
      this.updateForm(subjectMaster);

      this.subCategoryMasterService
        .query()
        .subscribe((res: HttpResponse<ISubCategoryMaster[]>) => (this.subcategorymasters = res.body || []));
    });
  }

  updateForm(subjectMaster: ISubjectMaster): void {
    this.editForm.patchValue({
      id: subjectMaster.id,
      name: subjectMaster.name,
      subCategoryMaster: subjectMaster.subCategoryMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subjectMaster = this.createFromForm();
    if (subjectMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.subjectMasterService.update(subjectMaster));
    } else {
      this.subscribeToSaveResponse(this.subjectMasterService.create(subjectMaster));
    }
  }

  private createFromForm(): ISubjectMaster {
    return {
      ...new SubjectMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      subCategoryMaster: this.editForm.get(['subCategoryMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubjectMaster>>): void {
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

  trackById(index: number, item: ISubCategoryMaster): any {
    return item.id;
  }
}
