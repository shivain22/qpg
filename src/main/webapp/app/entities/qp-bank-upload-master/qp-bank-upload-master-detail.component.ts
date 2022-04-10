import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IQpBankUploadMaster } from 'app/shared/model/qp-bank-upload-master.model';

@Component({
  selector: 'jhi-qp-bank-upload-master-detail',
  templateUrl: './qp-bank-upload-master-detail.component.html',
})
export class QpBankUploadMasterDetailComponent implements OnInit {
  qpBankUploadMaster: IQpBankUploadMaster | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qpBankUploadMaster }) => (this.qpBankUploadMaster = qpBankUploadMaster));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
