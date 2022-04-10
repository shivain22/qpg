import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConfigMaster } from '../../shared/model/config-master.model';

@Component({
  selector: 'jhi-college-master-detail',
  templateUrl: './config-master-detail.component.html',
})
export class ConfigMasterDetailComponent implements OnInit {
  configMaster: IConfigMaster | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configMaster }) => (this.configMaster = configMaster));
  }

  previousState(): void {
    window.history.back();
  }
}
