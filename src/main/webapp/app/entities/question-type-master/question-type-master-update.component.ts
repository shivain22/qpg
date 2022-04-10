import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionTypeMaster, QuestionTypeMaster } from 'app/shared/model/question-type-master.model';
import { QuestionTypeMasterService } from './question-type-master.service';

@Component({
  selector: 'jhi-question-type-master-update',
  templateUrl: './question-type-master-update.component.html',
})
export class QuestionTypeMasterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    shortName: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
  });

  constructor(
    protected questionTypeMasterService: QuestionTypeMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionTypeMaster }) => {
      this.updateForm(questionTypeMaster);
    });
  }

  updateForm(questionTypeMaster: IQuestionTypeMaster): void {
    this.editForm.patchValue({
      id: questionTypeMaster.id,
      name: questionTypeMaster.name,
      shortName: questionTypeMaster.shortName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionTypeMaster = this.createFromForm();
    if (questionTypeMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.questionTypeMasterService.update(questionTypeMaster));
    } else {
      this.subscribeToSaveResponse(this.questionTypeMasterService.create(questionTypeMaster));
    }
  }

  private createFromForm(): IQuestionTypeMaster {
    return {
      ...new QuestionTypeMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      shortName: this.editForm.get(['shortName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionTypeMaster>>): void {
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
