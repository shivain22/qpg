import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITestEntity } from 'app/shared/model/test-entity.model';

@Component({
  selector: 'jhi-test-entity-detail',
  templateUrl: './test-entity-detail.component.html',
})
export class TestEntityDetailComponent implements OnInit {
  testEntity: ITestEntity | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ testEntity }) => (this.testEntity = testEntity));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
