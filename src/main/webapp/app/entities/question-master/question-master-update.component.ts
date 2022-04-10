import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionMaster, QuestionMaster } from 'app/shared/model/question-master.model';
import { QuestionMasterService } from './question-master.service';
import { IQuestionTypeMaster } from 'app/shared/model/question-type-master.model';
import { QuestionTypeMasterService } from 'app/entities/question-type-master/question-type-master.service';
import { IDifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';
import { DifficultyTypeMasterService } from 'app/entities/difficulty-type-master/difficulty-type-master.service';
import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';
import { SubTopicMasterService } from 'app/entities/sub-topic-master/sub-topic-master.service';

type SelectableEntity = IQuestionTypeMaster | IDifficultyTypeMaster | ISubTopicMaster | IQuestionMaster;

@Component({
  selector: 'jhi-question-master-update',
  templateUrl: './question-master-update.component.html',
})
export class QuestionMasterUpdateComponent implements OnInit {
  isSaving = false;
  questiontypemasters: IQuestionTypeMaster[] = [];
  difficultytypemasters: IDifficultyTypeMaster[] = [];
  subtopicmasters: ISubTopicMaster[] = [];
  questionmasters: IQuestionMaster[] = [];

  editForm = this.fb.group({
    id: [],
    text: [],
    weightage: [],
    questionTypeMaster: [null, Validators.required],
    difficultyTypeMaster: [],
    subTopicMaster: [null, Validators.required],
    parentQuestionMaster: [],
  });

  constructor(
    protected questionMasterService: QuestionMasterService,
    protected questionTypeMasterService: QuestionTypeMasterService,
    protected difficultyTypeMasterService: DifficultyTypeMasterService,
    protected subTopicMasterService: SubTopicMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionMaster }) => {
      this.updateForm(questionMaster);

      this.questionTypeMasterService
        .query()
        .subscribe((res: HttpResponse<IQuestionTypeMaster[]>) => (this.questiontypemasters = res.body || []));

      this.difficultyTypeMasterService
        .query()
        .subscribe((res: HttpResponse<IDifficultyTypeMaster[]>) => (this.difficultytypemasters = res.body || []));

      this.subTopicMasterService.query().subscribe((res: HttpResponse<ISubTopicMaster[]>) => (this.subtopicmasters = res.body || []));

      this.questionMasterService.query().subscribe((res: HttpResponse<IQuestionMaster[]>) => (this.questionmasters = res.body || []));
    });
  }

  updateForm(questionMaster: IQuestionMaster): void {
    this.editForm.patchValue({
      id: questionMaster.id,
      text: questionMaster.text,
      weightage: questionMaster.weightage,
      questionTypeMaster: questionMaster.questionTypeMaster,
      difficultyTypeMaster: questionMaster.difficultyTypeMaster,
      subTopicMaster: questionMaster.subTopicMaster,
      parentQuestionMaster: questionMaster.parentQuestionMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionMaster = this.createFromForm();
    if (questionMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.questionMasterService.update(questionMaster));
    } else {
      this.subscribeToSaveResponse(this.questionMasterService.create(questionMaster));
    }
  }

  private createFromForm(): IQuestionMaster {
    return {
      ...new QuestionMaster(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      weightage: this.editForm.get(['weightage'])!.value,
      questionTypeMaster: this.editForm.get(['questionTypeMaster'])!.value,
      difficultyTypeMaster: this.editForm.get(['difficultyTypeMaster'])!.value,
      subTopicMaster: this.editForm.get(['subTopicMaster'])!.value,
      parentQuestionMaster: this.editForm.get(['parentQuestionMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionMaster>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
