import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategoryMaster, CategoryMaster } from 'app/shared/model/category-master.model';
import { CategoryMasterService } from './category-master.service';

@Component({
  selector: 'jhi-category-master-update',
  templateUrl: './category-master-update.component.html',
})
export class CategoryMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
  });

  constructor(protected categoryMasterService: CategoryMasterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryMaster }) => {
      this.updateForm(categoryMaster);
    });
  }

  updateForm(categoryMaster: ICategoryMaster): void {
    this.editForm.patchValue({
      id: categoryMaster.id,
      name: categoryMaster.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoryMaster = this.createFromForm();
    if (categoryMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryMasterService.update(categoryMaster));
    } else {
      this.subscribeToSaveResponse(this.categoryMasterService.create(categoryMaster));
    }
  }

  private createFromForm(): ICategoryMaster {
    return {
      ...new CategoryMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryMaster>>): void {
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
