import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnswerMaster, AnswerMaster } from 'app/shared/model/answer-master.model';
import { AnswerMasterService } from './answer-master.service';
import { IQuestionMaster } from 'app/shared/model/question-master.model';
import { QuestionMasterService } from 'app/entities/question-master/question-master.service';

@Component({
  selector: 'jhi-answer-master-update',
  templateUrl: './answer-master-update.component.html',
})
export class AnswerMasterUpdateComponent implements OnInit {
  isSaving = false;
  questionmasters: IQuestionMaster[] = [];

  editForm = this.fb.group({
    id: [],
    text: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(4000)]],
    correct: [null, [Validators.required]],
    questionMaster: [null, Validators.required],
  });

  constructor(
    protected answerMasterService: AnswerMasterService,
    protected questionMasterService: QuestionMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ answerMaster }) => {
      this.updateForm(answerMaster);

      this.questionMasterService.query().subscribe((res: HttpResponse<IQuestionMaster[]>) => (this.questionmasters = res.body || []));
    });
  }

  updateForm(answerMaster: IAnswerMaster): void {
    this.editForm.patchValue({
      id: answerMaster.id,
      text: answerMaster.text,
      correct: answerMaster.correct,
      questionMaster: answerMaster.questionMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const answerMaster = this.createFromForm();
    if (answerMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.answerMasterService.update(answerMaster));
    } else {
      this.subscribeToSaveResponse(this.answerMasterService.create(answerMaster));
    }
  }

  private createFromForm(): IAnswerMaster {
    return {
      ...new AnswerMaster(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      correct: this.editForm.get(['correct'])!.value,
      questionMaster: this.editForm.get(['questionMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerMaster>>): void {
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

  trackById(index: number, item: IQuestionMaster): any {
    return item.id;
  }
}
