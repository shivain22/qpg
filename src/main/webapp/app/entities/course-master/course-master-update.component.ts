import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICourseMaster, CourseMaster } from 'app/shared/model/course-master.model';
import { CourseMasterService } from './course-master.service';
import { IDepartmentMaster } from 'app/shared/model/department-master.model';
import { DepartmentMasterService } from 'app/entities/department-master/department-master.service';

@Component({
  selector: 'jhi-course-master-update',
  templateUrl: './course-master-update.component.html',
})
export class CourseMasterUpdateComponent implements OnInit {
  isSaving = false;
  departmentmasters: IDepartmentMaster[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
    departmentMaster: [null, Validators.required],
  });

  constructor(
    protected courseMasterService: CourseMasterService,
    protected departmentMasterService: DepartmentMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseMaster }) => {
      this.updateForm(courseMaster);

      this.departmentMasterService.query().subscribe((res: HttpResponse<IDepartmentMaster[]>) => (this.departmentmasters = res.body || []));
    });
  }

  updateForm(courseMaster: ICourseMaster): void {
    this.editForm.patchValue({
      id: courseMaster.id,
      name: courseMaster.name,
      departmentMaster: courseMaster.departmentMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const courseMaster = this.createFromForm();
    if (courseMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.courseMasterService.update(courseMaster));
    } else {
      this.subscribeToSaveResponse(this.courseMasterService.create(courseMaster));
    }
  }

  private createFromForm(): ICourseMaster {
    return {
      ...new CourseMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      departmentMaster: this.editForm.get(['departmentMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICourseMaster>>): void {
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

  trackById(index: number, item: IDepartmentMaster): any {
    return item.id;
  }
}
