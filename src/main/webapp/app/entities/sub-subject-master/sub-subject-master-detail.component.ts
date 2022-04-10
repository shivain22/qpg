import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubSubjectMaster } from 'app/shared/model/sub-subject-master.model';

@Component({
  selector: 'jhi-sub-subject-master-detail',
  templateUrl: './sub-subject-master-detail.component.html',
})
export class SubSubjectMasterDetailComponent implements OnInit {
  subSubjectMaster: ISubSubjectMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subSubjectMaster }) => (this.subSubjectMaster = subSubjectMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
