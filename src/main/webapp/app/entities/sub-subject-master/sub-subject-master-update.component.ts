import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubSubjectMaster, SubSubjectMaster } from 'app/shared/model/sub-subject-master.model';
import { SubSubjectMasterService } from './sub-subject-master.service';
import { ISubjectMaster } from 'app/shared/model/subject-master.model';
import { SubjectMasterService } from 'app/entities/subject-master/subject-master.service';

@Component({
  selector: 'jhi-sub-subject-master-update',
  templateUrl: './sub-subject-master-update.component.html',
})
export class SubSubjectMasterUpdateComponent implements OnInit {
  isSaving = false;
  subjectmasters: ISubjectMaster[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    subjectMaster: [null, Validators.required],
  });

  constructor(
    protected subSubjectMasterService: SubSubjectMasterService,
    protected subjectMasterService: SubjectMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subSubjectMaster }) => {
      this.updateForm(subSubjectMaster);

      this.subjectMasterService.query().subscribe((res: HttpResponse<ISubjectMaster[]>) => (this.subjectmasters = res.body || []));
    });
  }

  updateForm(subSubjectMaster: ISubSubjectMaster): void {
    this.editForm.patchValue({
      id: subSubjectMaster.id,
      name: subSubjectMaster.name,
      subjectMaster: subSubjectMaster.subjectMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subSubjectMaster = this.createFromForm();
    if (subSubjectMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.subSubjectMasterService.update(subSubjectMaster));
    } else {
      this.subscribeToSaveResponse(this.subSubjectMasterService.create(subSubjectMaster));
    }
  }

  private createFromForm(): ISubSubjectMaster {
    return {
      ...new SubSubjectMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      subjectMaster: this.editForm.get(['subjectMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubSubjectMaster>>): void {
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

  trackById(index: number, item: ISubjectMaster): any {
    return item.id;
  }
}
