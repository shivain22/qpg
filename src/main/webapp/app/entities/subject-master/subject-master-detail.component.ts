import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubjectMaster } from 'app/shared/model/subject-master.model';

@Component({
  selector: 'jhi-subject-master-detail',
  templateUrl: './subject-master-detail.component.html',
})
export class SubjectMasterDetailComponent implements OnInit {
  subjectMaster: ISubjectMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subjectMaster }) => (this.subjectMaster = subjectMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
