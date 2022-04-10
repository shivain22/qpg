import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IQpBankUploadMaster, QpBankUploadMaster } from 'app/shared/model/qp-bank-upload-master.model';
import { QpBankUploadMasterService } from './qp-bank-upload-master.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';
import { SubTopicMasterService } from 'app/entities/sub-topic-master/sub-topic-master.service';

@Component({
  selector: 'jhi-qp-bank-upload-master-update',
  templateUrl: './qp-bank-upload-master-update.component.html',
})
export class QpBankUploadMasterUpdateComponent implements OnInit {
  isSaving = false;
  subtopicmasters: ISubTopicMaster[] = [];

  editForm = this.fb.group({
    id: [],
    qpBankFile: [null, [Validators.required]],
    qpBankFileContentType: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
    subTopicMaster: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected qpBankUploadMasterService: QpBankUploadMasterService,
    protected subTopicMasterService: SubTopicMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qpBankUploadMaster }) => {
      this.updateForm(qpBankUploadMaster);

      this.subTopicMasterService.query().subscribe((res: HttpResponse<ISubTopicMaster[]>) => (this.subtopicmasters = res.body || []));
    });
  }

  updateForm(qpBankUploadMaster: IQpBankUploadMaster): void {
    this.editForm.patchValue({
      id: qpBankUploadMaster.id,
      qpBankFile: qpBankUploadMaster.qpBankFile,
      qpBankFileContentType: qpBankUploadMaster.qpBankFileContentType,
      name: qpBankUploadMaster.name,
      subTopicMaster: qpBankUploadMaster.subTopicMaster,
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
    const qpBankUploadMaster = this.createFromForm();
    if (qpBankUploadMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.qpBankUploadMasterService.update(qpBankUploadMaster));
    } else {
      this.subscribeToSaveResponse(this.qpBankUploadMasterService.create(qpBankUploadMaster));
    }
  }

  private createFromForm(): IQpBankUploadMaster {
    return {
      ...new QpBankUploadMaster(),
      id: this.editForm.get(['id'])!.value,
      qpBankFileContentType: this.editForm.get(['qpBankFileContentType'])!.value,
      qpBankFile: this.editForm.get(['qpBankFile'])!.value,
      name: this.editForm.get(['name'])!.value,
      subTopicMaster: this.editForm.get(['subTopicMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQpBankUploadMaster>>): void {
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

  trackById(index: number, item: ISubTopicMaster): any {
    return item.id;
  }
}
