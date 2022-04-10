import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';

@Component({
  selector: 'jhi-difficulty-type-master-detail',
  templateUrl: './difficulty-type-master-detail.component.html',
})
export class DifficultyTypeMasterDetailComponent implements OnInit {
  difficultyTypeMaster: IDifficultyTypeMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ difficultyTypeMaster }) => (this.difficultyTypeMaster = difficultyTypeMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
