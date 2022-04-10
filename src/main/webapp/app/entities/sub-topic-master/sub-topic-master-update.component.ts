import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubTopicMaster, SubTopicMaster } from 'app/shared/model/sub-topic-master.model';
import { SubTopicMasterService } from './sub-topic-master.service';
import { ITopicMaster } from 'app/shared/model/topic-master.model';
import { TopicMasterService } from 'app/entities/topic-master/topic-master.service';

@Component({
  selector: 'jhi-sub-topic-master-update',
  templateUrl: './sub-topic-master-update.component.html',
})
export class SubTopicMasterUpdateComponent implements OnInit {
  isSaving = false;
  topicmasters: ITopicMaster[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    topicMaster: [null, Validators.required],
  });

  constructor(
    protected subTopicMasterService: SubTopicMasterService,
    protected topicMasterService: TopicMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subTopicMaster }) => {
      this.updateForm(subTopicMaster);

      this.topicMasterService.query().subscribe((res: HttpResponse<ITopicMaster[]>) => (this.topicmasters = res.body || []));
    });
  }

  updateForm(subTopicMaster: ISubTopicMaster): void {
    this.editForm.patchValue({
      id: subTopicMaster.id,
      name: subTopicMaster.name,
      topicMaster: subTopicMaster.topicMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subTopicMaster = this.createFromForm();
    if (subTopicMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.subTopicMasterService.update(subTopicMaster));
    } else {
      this.subscribeToSaveResponse(this.subTopicMasterService.create(subTopicMaster));
    }
  }

  private createFromForm(): ISubTopicMaster {
    return {
      ...new SubTopicMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      topicMaster: this.editForm.get(['topicMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubTopicMaster>>): void {
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

  trackById(index: number, item: ITopicMaster): any {
    return item.id;
  }
}
