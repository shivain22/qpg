import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IQuestionBankMaster } from 'app/shared/model/question-bank-master.model';

@Component({
  selector: 'jhi-question-bank-master-detail',
  templateUrl: './question-bank-master-detail.component.html',
})
export class QuestionBankMasterDetailComponent implements OnInit {
  questionBankMaster: IQuestionBankMaster | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionBankMaster }) => (this.questionBankMaster = questionBankMaster));
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
