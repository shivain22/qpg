import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepartmentMaster } from 'app/shared/model/department-master.model';

@Component({
  selector: 'jhi-department-master-detail',
  templateUrl: './department-master-detail.component.html',
})
export class DepartmentMasterDetailComponent implements OnInit {
  departmentMaster: IDepartmentMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ departmentMaster }) => (this.departmentMaster = departmentMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
