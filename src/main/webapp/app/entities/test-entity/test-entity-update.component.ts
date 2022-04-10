import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITestEntity, TestEntity } from 'app/shared/model/test-entity.model';
import { TestEntityService } from './test-entity.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-test-entity-update',
  templateUrl: './test-entity-update.component.html',
})
export class TestEntityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    testFile: [null, [Validators.required]],
    testFileContentType: [],
    fileName: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected testEntityService: TestEntityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testEntity }) => {
      this.updateForm(testEntity);
    });
  }

  updateForm(testEntity: ITestEntity): void {
    this.editForm.patchValue({
      id: testEntity.id,
      testFile: testEntity.testFile,
      testFileContentType: testEntity.testFileContentType,
      fileName: testEntity.fileName,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('qpgApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const testEntity = this.createFromForm();
    if (testEntity.id !== undefined) {
      this.subscribeToSaveResponse(this.testEntityService.update(testEntity));
    } else {
      this.subscribeToSaveResponse(this.testEntityService.create(testEntity));
    }
  }

  private createFromForm(): ITestEntity {
    return {
      ...new TestEntity(),
      id: this.editForm.get(['id'])!.value,
      testFileContentType: this.editForm.get(['testFileContentType'])!.value,
      testFile: this.editForm.get(['testFile'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITestEntity>>): void {
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
