import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourseMaster } from 'app/shared/model/course-master.model';

@Component({
  selector: 'jhi-course-master-detail',
  templateUrl: './course-master-detail.component.html',
})
export class CourseMasterDetailComponent implements OnInit {
  courseMaster: ICourseMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courseMaster }) => (this.courseMaster = courseMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
