import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubCategoryMaster } from 'app/shared/model/sub-category-master.model';

@Component({
  selector: 'jhi-sub-category-master-detail',
  templateUrl: './sub-category-master-detail.component.html',
})
export class SubCategoryMasterDetailComponent implements OnInit {
  subCategoryMaster: ISubCategoryMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subCategoryMaster }) => (this.subCategoryMaster = subCategoryMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
