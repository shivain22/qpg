import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDepartmentMaster, DepartmentMaster } from 'app/shared/model/department-master.model';
import { DepartmentMasterService } from './department-master.service';
import { ICollegeMaster } from 'app/shared/model/college-master.model';
import { CollegeMasterService } from 'app/entities/college-master/college-master.service';

@Component({
  selector: 'jhi-department-master-update',
  templateUrl: './department-master-update.component.html',
})
export class DepartmentMasterUpdateComponent implements OnInit {
  isSaving = false;
  collegemasters: ICollegeMaster[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
    collegeMaster: [null, Validators.required],
  });

  constructor(
    protected departmentMasterService: DepartmentMasterService,
    protected collegeMasterService: CollegeMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departmentMaster }) => {
      this.updateForm(departmentMaster);

      this.collegeMasterService.query().subscribe((res: HttpResponse<ICollegeMaster[]>) => (this.collegemasters = res.body || []));
    });
  }

  updateForm(departmentMaster: IDepartmentMaster): void {
    this.editForm.patchValue({
      id: departmentMaster.id,
      name: departmentMaster.name,
      collegeMaster: departmentMaster.collegeMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const departmentMaster = this.createFromForm();
    if (departmentMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.departmentMasterService.update(departmentMaster));
    } else {
      this.subscribeToSaveResponse(this.departmentMasterService.create(departmentMaster));
    }
  }

  private createFromForm(): IDepartmentMaster {
    return {
      ...new DepartmentMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      collegeMaster: this.editForm.get(['collegeMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartmentMaster>>): void {
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

  trackById(index: number, item: ICollegeMaster): any {
    return item.id;
  }
}
