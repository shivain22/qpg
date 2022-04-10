import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICollegeMaster } from 'app/shared/model/college-master.model';

@Component({
  selector: 'jhi-college-master-detail',
  templateUrl: './college-master-detail.component.html',
})
export class CollegeMasterDetailComponent implements OnInit {
  collegeMaster: ICollegeMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collegeMaster }) => (this.collegeMaster = collegeMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
