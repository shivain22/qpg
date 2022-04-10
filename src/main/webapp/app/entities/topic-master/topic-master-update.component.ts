import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITopicMaster, TopicMaster } from 'app/shared/model/topic-master.model';
import { TopicMasterService } from './topic-master.service';
import { ISubSubjectMaster } from 'app/shared/model/sub-subject-master.model';
import { SubSubjectMasterService } from 'app/entities/sub-subject-master/sub-subject-master.service';

@Component({
  selector: 'jhi-topic-master-update',
  templateUrl: './topic-master-update.component.html',
})
export class TopicMasterUpdateComponent implements OnInit {
  isSaving = false;
  subsubjectmasters: ISubSubjectMaster[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    shortCode: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    subSubjectMaster: [null, Validators.required],
  });

  constructor(
    protected topicMasterService: TopicMasterService,
    protected subSubjectMasterService: SubSubjectMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topicMaster }) => {
      this.updateForm(topicMaster);

      this.subSubjectMasterService.query().subscribe((res: HttpResponse<ISubSubjectMaster[]>) => (this.subsubjectmasters = res.body || []));
    });
  }

  updateForm(topicMaster: ITopicMaster): void {
    this.editForm.patchValue({
      id: topicMaster.id,
      name: topicMaster.name,
      shortCode: topicMaster.shortCode,
      subSubjectMaster: topicMaster.subSubjectMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const topicMaster = this.createFromForm();
    if (topicMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.topicMasterService.update(topicMaster));
    } else {
      this.subscribeToSaveResponse(this.topicMasterService.create(topicMaster));
    }
  }

  private createFromForm(): ITopicMaster {
    return {
      ...new TopicMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      shortCode: this.editForm.get(['shortCode'])!.value,
      subSubjectMaster: this.editForm.get(['subSubjectMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopicMaster>>): void {
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

  trackById(index: number, item: ISubSubjectMaster): any {
    return item.id;
  }
}
