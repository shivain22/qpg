import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { AbstractControl, Form, FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionBluePrintMaster, QuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';
import { QuestionBluePrintMasterService } from './question-blue-print-master.service';
import { IExamMaster } from 'app/shared/model/exam-master.model';

import { IQuestionTypeMaster } from '../../shared/model/question-type-master.model';

import { QuestionTypeMasterService } from '../question-type-master/question-type-master.service';
import { IDifficultyTypeMaster } from '../../shared/model/difficulty-type-master.model';
import { DifficultyTypeMasterService } from '../difficulty-type-master/difficulty-type-master.service';
import { IQuestionBluePrintDetail } from '../../shared/model/question-blue-print-details.model';
import { QuestionChoiceMasterService } from '../question-choice-master/question-type-master.service';
import { IQuestionChoiceMaster } from '../../shared/model/question-choice-master.model';
import { IQuestionTypeCategoryMaster } from '../../shared/model/question-type-category-master.model';

@Component({
  selector: 'jhi-question-blue-print-master-update',
  templateUrl: './question-blue-print-master-update.component.html',
})
export class QuestionBluePrintMasterUpdateComponent implements OnInit {
  isSaving = false;
  questiontypemasters: IQuestionTypeMaster[] = [];
  questionchoicemasters: IQuestionTypeMaster[] = [];
  difficultytypemasters: IDifficultyTypeMaster[] = [];
  partAtotalMarks = 0;
  partBtotalMarks = 0;
  partaDuration = 0;
  partbDuration = 0;
  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    totalMarks: [0],
    partaDuration: [0],
    partbDuration: [0],
    description: [],
    questionBluePrintDetails: this.fb.array([this.initQuestionBluePrintDetails()]),
  });

  constructor(
    protected questionBluePrintMasterService: QuestionBluePrintMasterService,
    protected questionTypeMasterService: QuestionTypeMasterService,
    protected questionChoiceMasterService: QuestionChoiceMasterService,
    protected difficultyTypeMasterService: DifficultyTypeMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  getQuestionBluePrintDetails(): AbstractControl[] {
    return (this.editForm.controls.questionBluePrintDetails as FormArray).controls;
  }

  initQuestionBluePrintDetails(): FormGroup {
    return this.fb.group({
      id: [],
      totalQuestions: [null, [Validators.required]],
      questionTypeMaster: this.initQuestionTypeMaster(),
      questionBluePrintMaster: [null],
      numOfChoices: [],
      questionChoiceMaster: [null],
      difficultyTypeMaster: [null],
    });
  }

  initQuestionTypeMaster(): FormGroup {
    return this.fb.group({
      id: [],
      name: [null],
      shortName: [null],
    });
  }

  initQuestionChoiceMaster(): FormGroup {
    return this.fb.group({
      id: [],
      name: [null],
      shortName: [null],
    });
  }

  initDifficultyTypeMaster(): FormGroup {
    return this.fb.group({
      id: [],
      name: [null],
      shortName: [null],
    });
  }

  addQuestionBluePrintDetails(
    qtm: IQuestionTypeMaster | undefined,
    qcm: IQuestionChoiceMaster | undefined,
    dtm: IDifficultyTypeMaster | undefined
  ): void {
    const control = this.editForm.controls['questionBluePrintDetails'] as FormArray;
    control.push(
      this.fb.group({
        id: [],
        totalQuestions: [0],
        questionTypeMaster: this.addQuestionTypeMaster(qtm),
        questionBluePrintMaster: [],
        numOfChoices: [0],
        questionChoiceMaster: [qcm],
        difficultyTypeMaster: [dtm],
      })
    );
  }

  addQuestionBluePrintDetailsEdit(qtm: IQuestionTypeMaster | undefined, qbpdtl: IQuestionBluePrintDetail | undefined): void {
    /* eslint-disable no-console */
    const control = this.editForm.controls['questionBluePrintDetails'] as FormArray;
    control.push(
      this.fb.group({
        id: [qbpdtl?.id],
        totalQuestions: [qbpdtl?.totalQuestions],
        questionTypeMaster: this.addQuestionTypeMaster(qtm),
        questionBluePrintMaster: [],
        numOfChoices: [qbpdtl?.numOfChoices],
        questionChoiceMaster: [qbpdtl?.questionChoiceMaster],
        difficultyTypeMaster: [qbpdtl?.difficultyTypeMaster],
      })
    );
    /* eslint-enable no-console */
  }

  addQuestionTypeMaster(qtm: IQuestionTypeMaster | undefined): FormGroup {
    /* eslint-disable no-console */
    return this.fb.group({
      id: [qtm?.id],
      name: [qtm?.name, []],
      shortName: [qtm?.shortName, []],
      defaultWeightage: [qtm?.defaultWeightage, []],
      questionTypeCategoryMaster: this.addQuestionTypeCategoryMaster(qtm?.questionTypeCategoryMaster),
    });
    /* eslint-enable no-console */
  }

  addQuestionChoiceMaster(qcm: IQuestionChoiceMaster | undefined): FormGroup {
    /* eslint-disable no-console */
    return this.fb.group({
      id: [qcm?.id],
      name: [qcm?.name],
      shortName: [qcm?.shortName],
    });
    /* eslint-enable no-console */
  }

  addQuestionTypeCategoryMaster(qtcm: IQuestionTypeCategoryMaster | undefined): FormGroup {
    /* eslint-disable no-console */
    return this.fb.group({
      id: [qtcm?.id],
      name: [qtcm?.name],
      shortName: [qtcm?.shortName],
    });
    /* eslint-enable no-console */
  }

  addDifficultyTypeMaster(dtm: IDifficultyTypeMaster | undefined): FormGroup {
    /* eslint-disable no-console */
    return this.fb.group({
      id: [dtm?.id],
      name: [dtm?.name],
    });
    /* eslint-enable no-console */
  }

  removeQuestionBluePrintDetails(i: number): void {
    const control = this.editForm.controls['questionBluePrintDetails'] as FormArray;
    control.removeAt(i);
  }

  ngOnInit(): void {
    /* eslint-disable no-console */
    this.activatedRoute.data.subscribe(({ questionBluePrintMaster }) => {
      this.questionTypeMasterService.query().subscribe(
        (resQtm: HttpResponse<IQuestionTypeMaster[]>) => {
          this.questiontypemasters = resQtm.body || [];
          this.difficultyTypeMasterService.query().subscribe(
            (resDtm: HttpResponse<IDifficultyTypeMaster[]>) => {
              this.difficultytypemasters = resDtm.body || [];
              this.questionChoiceMasterService.query().subscribe(
                (resQcm: HttpResponse<IQuestionChoiceMaster[]>) => {
                  this.questionchoicemasters = resQcm.body || [];
                  if (questionBluePrintMaster.questionBluePrintDetails) {
                    console.log('Inside the setting loop');
                    const questionBluePrintDetails = this.editForm.controls.questionBluePrintDetails as FormArray;
                    questionBluePrintDetails.removeAt(0);
                    questionBluePrintMaster.questionBluePrintDetails.forEach((x: IQuestionBluePrintDetail | undefined) => {
                      this.addQuestionBluePrintDetailsEdit(x?.questionTypeMaster, x);
                    });
                  } else {
                    const questionBluePrintDetails = this.editForm.controls.questionBluePrintDetails as FormArray;
                    questionBluePrintDetails.removeAt(0);
                    questionBluePrintMaster.questionBluePrintDetails = [];
                    for (const questionTypeMaster of this.questiontypemasters) {
                      this.addQuestionBluePrintDetails(questionTypeMaster, this.questionchoicemasters[0], this.difficultytypemasters[0]);
                      questionBluePrintMaster.questionBluePrintDetails.push({});
                    }
                  }
                  questionBluePrintMaster.totalMarks = this.partAtotalMarks + this.partBtotalMarks;
                  this.updateForm(questionBluePrintMaster);
                },
                err => console.log(err),
                () => console.log('done question choice master')
              );
            },
            err => console.log(err),
            () => console.log('done difficulty master')
          );
        },
        err => console.log(err),
        () => console.log('done!')
      );
    });

    /* eslint-enable no-console */
  }

  updateForm(questionBluePrintMaster: IQuestionBluePrintMaster): void {
    /* eslint-disable no-console */
    this.editForm.get('questionBluePrintDetails')?.value.forEach((q: any) => {
      if (q.questionTypeMaster.questionTypeCategoryMaster.name === 'Part A') {
        this.partAtotalMarks += q.totalQuestions * q.questionTypeMaster.defaultWeightage;
      }
      if (q.questionTypeMaster.questionTypeCategoryMaster.name === 'Part B') {
        this.partBtotalMarks += q.totalQuestions * q.questionTypeMaster.defaultWeightage;
      }
    });
    questionBluePrintMaster.totalMarks = this.partBtotalMarks + this.partAtotalMarks;
    this.editForm.patchValue({
      id: questionBluePrintMaster.id,
      name: questionBluePrintMaster.name,
      description: questionBluePrintMaster.description,
      totalMarks: questionBluePrintMaster.totalMarks,
      partaDuration: questionBluePrintMaster.partaDuration,
      partbDuration: questionBluePrintMaster.partbDuration,
      questionBluePrintDetails: questionBluePrintMaster.questionBluePrintDetails,
    });

    console.log(this.editForm);
    /* eslint-enable no-console */
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionBluePrintMaster = this.createFromForm();
    if (questionBluePrintMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.questionBluePrintMasterService.update(questionBluePrintMaster));
    } else {
      this.subscribeToSaveResponse(this.questionBluePrintMasterService.create(questionBluePrintMaster));
    }
  }

  private createFromForm(): IQuestionBluePrintMaster {
    /* eslint-disable no-console */
    console.log(this.editForm.get(['questionBluePrintDetails'])!.value);
    return {
      ...new QuestionBluePrintMaster(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      totalMarks: this.editForm.get(['totalMarks'])!.value,
      partaDuration: this.editForm.get(['partaDuration'])!.value,
      partbDuration: this.editForm.get(['partbDuration'])!.value,
      questionBluePrintDetails: this.editForm.get(['questionBluePrintDetails'])!.value,
    };
    /* eslint-enable no-console */
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionBluePrintMaster>>): void {
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

  trackById(index: number, item: IExamMaster): any {
    return item.id;
  }

  trackByDifficultyMasterId(index: number, item: IDifficultyTypeMaster): any {
    return item.id;
  }

  trackByQuestionChoiceMasterId(index: number, item: IQuestionChoiceMaster): any {
    return item.id;
  }

  calculateTotalPartMarks(event: Event, qtm: IQuestionTypeMaster): any {
    /* eslint-disable no-console */
    this.partAtotalMarks = 0;
    this.partBtotalMarks = 0;
    this.editForm.get('questionBluePrintDetails')?.value.forEach((q: any) => {
      if (q.questionTypeMaster.questionTypeCategoryMaster.name === 'Part A') {
        this.partAtotalMarks += q.totalQuestions * q.questionTypeMaster.defaultWeightage;
      }
      if (q.questionTypeMaster.questionTypeCategoryMaster.name === 'Part B') {
        this.partBtotalMarks += q.totalQuestions * q.questionTypeMaster.defaultWeightage;
      }
    });
    this.editForm.get(['totalMarks'])?.setValue(this.partBtotalMarks + this.partAtotalMarks);
    console.log(qtm.name);
    /* eslint-enable no-console */
  }
}
