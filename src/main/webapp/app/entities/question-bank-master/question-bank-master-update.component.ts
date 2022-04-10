import {Component, OnInit} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import * as _ from 'lodash';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {FormBuilder, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Observable} from 'rxjs';
import {JhiDataUtils, JhiEventManager, JhiEventWithContent, JhiFileLoadError} from 'ng-jhipster';

import {IQuestionBankMaster, QuestionBankMaster} from 'app/shared/model/question-bank-master.model';
import {QuestionBankMasterService} from './question-bank-master.service';
import {AlertError} from 'app/shared/alert/alert-error.model';
import {ICollegeMaster} from 'app/shared/model/college-master.model';
import {CollegeMasterService} from 'app/entities/college-master/college-master.service';
import {IDepartmentMaster} from 'app/shared/model/department-master.model';
import {DepartmentMasterService} from 'app/entities/department-master/department-master.service';
import {ICourseMaster} from 'app/shared/model/course-master.model';
import {CourseMasterService} from 'app/entities/course-master/course-master.service';

import {ICategoryMaster} from 'app/shared/model/category-master.model';
import {CategoryMasterService} from 'app/entities/category-master/category-master.service';

import {ISubCategoryMaster} from 'app/shared/model/sub-category-master.model';
import {SubCategoryMasterService} from 'app/entities/sub-category-master/sub-category-master.service';
import {ISubjectMaster} from 'app/shared/model/subject-master.model';
import {ISubSubjectMaster} from 'app/shared/model/sub-subject-master.model';
import {ITopicMaster} from 'app/shared/model/topic-master.model';
import {ISubTopicMaster} from 'app/shared/model/sub-topic-master.model';
import {SubjectMasterService} from 'app/entities/subject-master/subject-master.service';
import {SubSubjectMasterService} from 'app/entities/sub-subject-master/sub-subject-master.service';
import {TopicMasterService} from 'app/entities/topic-master/topic-master.service';
import {SubTopicMasterService} from 'app/entities/sub-topic-master/sub-topic-master.service';

@Component({
  selector: 'jhi-question-bank-master-update',
  templateUrl: './question-bank-master-update.component.html',
})
export class QuestionBankMasterUpdateComponent implements OnInit {
  isSaving = false;
  collegemasters: ICollegeMaster[] = [];
  departmentmasters: IDepartmentMaster[] = [];

  coursemasters: ICourseMaster[] = [];
  categorymasters: ICategoryMaster[] = [];
  subcategorymasters: ISubCategoryMaster[] = [];
  subjectmasters: ISubjectMaster[] = [];
  subsubjectmasters: ISubSubjectMaster[] = [];
  topicmasters: ITopicMaster[] = [];
  subtopicmasters: ISubTopicMaster[] = [];

  filtereddepartmentmasters: IDepartmentMaster[] = [];
  filteredcoursemasters: ICourseMaster[] = [];
  filteredcategorymasters: ICategoryMaster[] = [];
  filteredsubcategorymasters: ISubCategoryMaster[] = [];
  filteredsubjectmasters: ISubjectMaster[] = [];
  filteredsubsubjectmasters: ISubSubjectMaster[] = [];
  filteredtopicmasters: ITopicMaster[] = [];
  filteredsubtopicmasters: ISubTopicMaster[] = [];


  editForm = this.fb.group({
    id: [],
    questionBankFile: [null, [Validators.required]],
    questionBankFileContentType: [],
    collegeMaster: [null, Validators.required],
    departmentMaster: [null, Validators.required],
    courseMaster: [null, Validators.required],
    categoryMaster: [null, Validators.required],
    subCategoryMaster: [null, Validators.required],
    subjectMaster: [null, Validators.required],
    subSubjectMaster: [null, Validators.required],
    topicMaster: [null, Validators.required],
    subTopicMaster: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected collegeMasterService: CollegeMasterService,
    protected questionBankMasterService: QuestionBankMasterService,
    protected departmentMasterService: DepartmentMasterService,
    protected courseMasterService: CourseMasterService,
    protected categoryMasterService: CategoryMasterService,
    protected subCategoryMasterService: SubCategoryMasterService,
    protected subjectMasterService: SubjectMasterService,
    protected subSubjectMasterService: SubSubjectMasterService,
    protected topicMasterService: TopicMasterService,
    protected subTopicMasterService: SubTopicMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionBankMaster }) => {
      this.updateForm(questionBankMaster);
      this.collegeMasterService.query().subscribe((res: HttpResponse<ICollegeMaster[]>) => (this.collegemasters = res.body || []));
      this.departmentMasterService.query().subscribe((res: HttpResponse<IDepartmentMaster[]>) => (this.departmentmasters = res.body || []));
      this.courseMasterService.query().subscribe((res: HttpResponse<ICourseMaster[]>) => (this.coursemasters = res.body || []));
      this.categoryMasterService.query().subscribe((res: HttpResponse<ICategoryMaster[]>) => (this.categorymasters = res.body || []));
      this.subCategoryMasterService.query().subscribe((res: HttpResponse<ISubCategoryMaster[]>) => (this.subcategorymasters = res.body || []));
      this.subjectMasterService.query().subscribe((res: HttpResponse<ISubjectMaster[]>) => (this.subjectmasters = res.body || []));
      this.subSubjectMasterService.query().subscribe((res: HttpResponse<ISubSubjectMaster[]>) => (this.subsubjectmasters = res.body || []));
      this.topicMasterService.query().subscribe((res: HttpResponse<ITopicMaster[]>) => (this.topicmasters = res.body || []));
      this.subTopicMasterService.query().subscribe((res: HttpResponse<ISubTopicMaster[]>) => (this.subtopicmasters = res.body || []));
    });

    this.editForm.get('collegeMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('departmentMaster')?.setValue([]);
      this.editForm.get('courseMaster')?.setValue([]);
      this.editForm.get('categoryMaster')?.setValue([]);
      this.editForm.get('subCategoryMaster')?.setValue([]);
      this.editForm.get('subjectMaster')?.setValue([]);
      this.editForm.get('subSubjectMaster')?.setValue([]);
      this.editForm.get('topicMaster')?.setValue([]);
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filtereddepartmentmasters = _.filter(this.departmentmasters, c => c?.collegeMaster?.id === item?.id);

    });
    this.editForm.get('departmentMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('courseMaster')?.setValue([]);
      this.editForm.get('categoryMaster')?.setValue([]);
      this.editForm.get('subCategoryMaster')?.setValue([]);
      this.editForm.get('subjectMaster')?.setValue([]);
      this.editForm.get('subSubjectMaster')?.setValue([]);
      this.editForm.get('topicMaster')?.setValue([]);
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filteredcoursemasters = _.filter(this.coursemasters, c => c?.departmentMaster?.id === item?.id);

    });

    this.editForm.get('courseMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('categoryMaster')?.setValue([]);
      this.editForm.get('subCategoryMaster')?.setValue([]);
      this.editForm.get('subjectMaster')?.setValue([]);
      this.editForm.get('subSubjectMaster')?.setValue([]);
      this.editForm.get('topicMaster')?.setValue([]);
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filteredcategorymasters = _.filter(this.categorymasters, c => c?.courseMaster?.id === item?.id);

    });

    this.editForm.get('categoryMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('subCategoryMaster')?.setValue([]);
      this.editForm.get('subjectMaster')?.setValue([]);
      this.editForm.get('subSubjectMaster')?.setValue([]);
      this.editForm.get('topicMaster')?.setValue([]);
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filteredsubcategorymasters = _.filter(this.subcategorymasters, c =>c?.categoryMaster?.id === item?.id);

    });

    this.editForm.get('subCategoryMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('subjectMaster')?.setValue([]);
      this.editForm.get('subSubjectMaster')?.setValue([]);
      this.editForm.get('topicMaster')?.setValue([]);
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filteredsubjectmasters = _.filter(this.subjectmasters, c => c?.subCategoryMaster?.id === item?.id);

    });

    this.editForm.get('subjectMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('subSubjectMaster')?.setValue([]);
      this.editForm.get('topicMaster')?.setValue([]);
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filteredsubsubjectmasters = _.filter(this.subsubjectmasters, c => c?.subjectMaster?.id === item?.id);

    });

    this.editForm.get('subSubjectMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('topicMaster')?.setValue([]);
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filteredtopicmasters = _.filter(this.topicmasters, c => c?.subSubjectMaster?.id === item?.id);

    });

    this.editForm.get('topicMaster')!.valueChanges.subscribe(item => {
      this.editForm.get('subTopicMaster')?.setValue([]);
      this.filteredsubtopicmasters = _.filter(this.subtopicmasters, c => c?.topicMaster?.id === item?.id);

    });

  }

  updateForm(questionBankMaster: IQuestionBankMaster): void {
    this.editForm.patchValue({
      id: questionBankMaster.id,
      questionBankFile: questionBankMaster.questionBankFile,
      questionBankFileContentType: questionBankMaster.questionBankFileContentType,
      collegeMaster: questionBankMaster.collegeMaster,
      departmentMaster: questionBankMaster.departmentMaster,
      courseMaster: questionBankMaster.courseMaster,
      categoryMaster: questionBankMaster.categoryMaster,
      subCategoryMaster: questionBankMaster.subCategoryMaster,
      subjectMaster: questionBankMaster.subjectMaster,
      subSubjectMaster: questionBankMaster.subSubjectMaster,
      topicMaster: questionBankMaster.topicMaster,
      subTopicMaster: questionBankMaster.subTopicMaster,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('qpgApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionBankMaster = this.createFromForm();
    /* eslint-disable no-console */
    console.log(questionBankMaster);
    /* eslint-enable no-console */
    if (questionBankMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.questionBankMasterService.update(questionBankMaster));
    } else {
      this.subscribeToSaveResponse(this.questionBankMasterService.create(questionBankMaster));
    }
  }

  private createFromForm(): IQuestionBankMaster {
    /* eslint-disable no-console */
    if(this.editForm.get(['subTopicMaster'])!.value.length === 0){
      this.editForm.get(['subTopicMaster'])!.setValue({ });
    }
    /* eslint-enable no-console */
    return {
      ...new QuestionBankMaster(),
      id: this.editForm.get(['id'])!.value,
      questionBankFileContentType: this.editForm.get(['questionBankFileContentType'])!.value,
      questionBankFile: this.editForm.get(['questionBankFile'])!.value,
      collegeMaster: this.editForm.get(['collegeMaster'])!.value,
      departmentMaster: this.editForm.get(['departmentMaster'])!.value,
      courseMaster: this.editForm.get(['courseMaster'])!.value,
      categoryMaster: this.editForm.get(['categoryMaster'])!.value,
      subCategoryMaster: this.editForm.get(['subCategoryMaster'])!.value,
      subjectMaster: this.editForm.get(['subjectMaster'])!.value,
      subSubjectMaster: this.editForm.get(['subSubjectMaster'])!.value,
      topicMaster: this.editForm.get(['topicMaster'])!.value,
      subTopicMaster: this.editForm.get(['subTopicMaster'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionBankMaster>>): void {
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

  trackById(index: number, item: ICollegeMaster): any {
    return item.id;
  }
  trackByDeptId(index: number, item: IDepartmentMaster): any {
    return item.id;
  }

  trackByCourseId(index: number, item: ICourseMaster): any {
    return item.id;
  }

  trackByCategoryId(index: number, item: ICategoryMaster): any {
    return item.id;
  }

  trackBySubCategoryId(index: number, item: ISubCategoryMaster): any {
    return item.id;
  }

  trackBySubjectId(index: number, item: ISubjectMaster): any {
    return item.id;
  }

  trackBySubSubjectId(index: number, item: ISubSubjectMaster): any {
    return item.id;
  }

  trackByTopicId(index: number, item: ITopicMaster): any {
    return item.id;
  }

  trackBySubTopicId(index: number, item: ISubTopicMaster): any {
    return item.id;
  }



}
