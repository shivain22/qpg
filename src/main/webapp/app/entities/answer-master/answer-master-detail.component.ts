import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerMaster } from 'app/shared/model/answer-master.model';

@Component({
  selector: 'jhi-answer-master-detail',
  templateUrl: './answer-master-detail.component.html',
})
export class AnswerMasterDetailComponent implements OnInit {
  answerMaster: IAnswerMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ answerMaster }) => (this.answerMaster = answerMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
