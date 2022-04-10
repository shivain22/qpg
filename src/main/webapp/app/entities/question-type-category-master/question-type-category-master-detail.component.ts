import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionTypeMaster } from 'app/shared/model/question-type-master.model';

@Component({
  selector: 'jhi-question-type-master-detail',
  templateUrl: './question-type-category-master-detail.component.html',
})
export class QuestionTypeCategoryMasterDetailComponent implements OnInit {
  questionTypeMaster: IQuestionTypeMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionTypeMaster }) => (this.questionTypeMaster = questionTypeMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
