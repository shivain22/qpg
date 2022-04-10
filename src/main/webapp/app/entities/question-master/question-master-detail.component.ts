import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionMaster } from 'app/shared/model/question-master.model';

@Component({
  selector: 'jhi-question-master-detail',
  templateUrl: './question-master-detail.component.html',
})
export class QuestionMasterDetailComponent implements OnInit {
  questionMaster: IQuestionMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionMaster }) => (this.questionMaster = questionMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
