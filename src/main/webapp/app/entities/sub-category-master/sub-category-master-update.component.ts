import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubCategoryMaster, SubCategoryMaster } from 'app/shared/model/sub-category-master.model';
import { SubCategoryMasterService } from './sub-category-master.service';
import { ICategoryMaster } from 'app/shared/model/category-master.model';
import { CategoryMasterService } from 'app/entities/category-master/category-master.service';

@Component({
  selector: 'jhi-sub-category-master-update',
  templateUrl: './sub-category-master-update.component.html',
})
export class SubCategoryMasterUpdateComponent implements OnInit {
  isSaving = false;
  categorymasters: ICategoryMaster[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    categoryMaster: [null, Validators.required],
  });

  constructor(
    protected subCategoryMasterService: SubCategoryMasterService,
    protected categoryMasterService: CategoryMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subCategoryMaster }) => {
      this.updateForm(subCategoryMaster);

      this.categoryMasterService.query().subscribe((res: HttpResponse<ICategoryMaster[]>) => (this.categorymasters = res.body || []));
    });
  }

  updateForm(subCategoryMaster: ISubCategoryMaster): void {
    this.editForm.patchValue({
      id: subCategoryMaster.id,
      name: subCategoryMaster.name,
      categoryMaster: subCategoryMaster.categoryMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subCategoryMaster = this.createFromForm();
    if (subCategoryMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.subCategoryMasterService.update(subCategoryMaster));
    } else {
      this.subscribeToSaveResponse(this.subCategoryMasterService.create(subCategoryMaster));
    }
  }

  private createFromForm(): ISubCategoryMaster {
    return {
      ...new SubCategoryMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      categoryMaster: this.editForm.get(['categoryMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubCategoryMaster>>): void {
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

  trackById(index: number, item: ICategoryMaster): any {
    return item.id;
  }
}
