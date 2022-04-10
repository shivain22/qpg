import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';

@Component({
  selector: 'jhi-sub-topic-master-detail',
  templateUrl: './sub-topic-master-detail.component.html',
})
export class SubTopicMasterDetailComponent implements OnInit {
  subTopicMaster: ISubTopicMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subTopicMaster }) => (this.subTopicMaster = subTopicMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
