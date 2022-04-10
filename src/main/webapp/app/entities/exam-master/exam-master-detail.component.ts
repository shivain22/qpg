import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamMaster } from 'app/shared/model/exam-master.model';

@Component({
  selector: 'jhi-exam-master-detail',
  templateUrl: './exam-master-detail.component.html',
})
export class ExamMasterDetailComponent implements OnInit {
  examMaster: IExamMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examMaster }) => (this.examMaster = examMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
