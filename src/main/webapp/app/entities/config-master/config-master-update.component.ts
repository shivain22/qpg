import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ConfigMasterService } from './config-master.service';
import { ConfigMaster, IConfigMaster } from '../../shared/model/config-master.model';

@Component({
  selector: 'jhi-conifig-master-update',
  templateUrl: './config-master-update.component.html',
})
export class ConfigMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
    value: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(500)]],
  });

  constructor(protected configMasterService: ConfigMasterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ configMaster }) => {
      this.updateForm(configMaster);
    });
  }

  updateForm(configMaster: IConfigMaster): void {
    this.editForm.patchValue({
      id: configMaster.id,
      name: configMaster.name,
      value: configMaster.value,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const configMaster = this.createFromForm();
    if (configMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.configMasterService.update(configMaster));
    } else {
      this.subscribeToSaveResponse(this.configMasterService.create(configMaster));
    }
  }

  private createFromForm(): IConfigMaster {
    return {
      ...new ConfigMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConfigMaster>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
