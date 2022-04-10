import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITopicMaster } from 'app/shared/model/topic-master.model';

@Component({
  selector: 'jhi-topic-master-detail',
  templateUrl: './topic-master-detail.component.html',
})
export class TopicMasterDetailComponent implements OnInit {
  topicMaster: ITopicMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topicMaster }) => (this.topicMaster = topicMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
