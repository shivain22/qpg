import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';

@Component({
  selector: 'jhi-question-blue-print-master-detail',
  templateUrl: './question-blue-print-master-detail.component.html',
})
export class QuestionBluePrintMasterDetailComponent implements OnInit {
  questionBluePrintMaster: IQuestionBluePrintMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionBluePrintMaster }) => (this.questionBluePrintMaster = questionBluePrintMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
