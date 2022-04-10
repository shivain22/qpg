import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExamMaster, ExamMaster } from 'app/shared/model/exam-master.model';
import { ExamMasterService } from './exam-master.service';
import {ICollegeMaster} from "../../shared/model/college-master.model";
import {IDepartmentMaster} from "../../shared/model/department-master.model";
import {ICourseMaster} from "../../shared/model/course-master.model";
import {ICategoryMaster} from "../../shared/model/category-master.model";
import {ISubCategoryMaster} from "../../shared/model/sub-category-master.model";
import {ISubjectMaster} from "../../shared/model/subject-master.model";
import {ISubSubjectMaster} from "../../shared/model/sub-subject-master.model";
import {ITopicMaster} from "../../shared/model/topic-master.model";
import {ISubTopicMaster} from "../../shared/model/sub-topic-master.model";
import {CollegeMasterService} from "../college-master/college-master.service";
import {QuestionBankMasterService} from "../question-bank-master/question-bank-master.service";
import {DepartmentMasterService} from "../department-master/department-master.service";
import {CourseMasterService} from "../course-master/course-master.service";
import {CategoryMasterService} from "../category-master/category-master.service";
import {SubCategoryMasterService} from "../sub-category-master/sub-category-master.service";
import {SubjectMasterService} from "../subject-master/subject-master.service";
import {SubSubjectMasterService} from "../sub-subject-master/sub-subject-master.service";
import {TopicMasterService} from "../topic-master/topic-master.service";
import {SubTopicMasterService} from "../sub-topic-master/sub-topic-master.service";
import * as _ from "lodash";

@Component({
  selector: 'jhi-exam-master-update',
  templateUrl: './exam-master-update.component.html',
})
export class ExamMasterUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;

  collegemasters: ICollegeMaster[] = [];
  departmentmasters: IDepartmentMaster[] = [];
  coursemasters: ICourseMaster[] = [];
  categorymasters: ICategoryMaster[] = [];
  subcategorymasters: ISubCategoryMaster[] = [];
  subjectmasters: ISubjectMaster[] = [];
  subsubjectmasters: ISubSubjectMaster[] = [];
  topicmasters: ITopicMaster[] = [];
  subtopicmasters: ISubTopicMaster[] = [];

  filteredcollegemasters: ICollegeMaster[] = [];
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
    title: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
    startDate: [],
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
    protected examMasterService: ExamMasterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
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
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examMaster }) => {
      this.updateForm(examMaster);
      this.collegeMasterService.query().subscribe((res: HttpResponse<ICollegeMaster[]>) => (this.collegemasters = res.body || []));
      this.departmentMasterService.query().subscribe((res: HttpResponse<IDepartmentMaster[]>) => (this.departmentmasters = res.body || []));
      this.courseMasterService.query().subscribe((res: HttpResponse<ICourseMaster[]>) => (this.coursemasters = res.body || []));
      this.categoryMasterService.query().subscribe((res: HttpResponse<ICategoryMaster[]>) => (this.categorymasters = res.body || []));
      this.subCategoryMasterService.query().subscribe((res: HttpResponse<ISubCategoryMaster[]>) => (this.subcategorymasters = res.body || []));
      this.subjectMasterService.query().subscribe((res: HttpResponse<ISubjectMaster[]>) => (this.subjectmasters = res.body || []));
      this.subSubjectMasterService.query().subscribe((res: HttpResponse<ISubSubjectMaster[]>) => (this.subsubjectmasters = res.body || []));
      this.topicMasterService.query().subscribe((res: HttpResponse<ITopicMaster[]>) => (this.topicmasters = res.body || []));
      this.subTopicMasterService.query().subscribe((res: HttpResponse<ISubTopicMaster[]>) => (this.subtopicmasters = res.body || []));

      if(examMaster.id) {
        this.departmentMasterService.query().subscribe((res: HttpResponse<IDepartmentMaster[]>) => (this.filtereddepartmentmasters = res.body || []));
        this.courseMasterService.query().subscribe((res: HttpResponse<ICourseMaster[]>) => (this.filteredcoursemasters = res.body || []));
        this.categoryMasterService.query().subscribe((res: HttpResponse<ICategoryMaster[]>) => (this.filteredcategorymasters = res.body || []));
        this.subCategoryMasterService.query().subscribe((res: HttpResponse<ISubCategoryMaster[]>) => (this.filteredsubcategorymasters = res.body || []));
        this.subjectMasterService.query().subscribe((res: HttpResponse<ISubjectMaster[]>) => (this.filteredsubjectmasters = res.body || []));
        this.subSubjectMasterService.query().subscribe((res: HttpResponse<ISubSubjectMaster[]>) => (this.filteredsubsubjectmasters = res.body || []));
        this.topicMasterService.query().subscribe((res: HttpResponse<ITopicMaster[]>) => (this.filteredtopicmasters = res.body || []));
        this.subTopicMasterService.query().subscribe((res: HttpResponse<ISubTopicMaster[]>) => (this.filteredsubtopicmasters = res.body || []));
      }
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

  updateForm(examMaster: IExamMaster): void {
    if(this.editForm.get(['subjectMaster'])!.value?.length === 0){
      this.editForm.get(['subjectMaster'])!.setValue({ });
    }
    if(this.editForm.get(['subSubjectMaster'])!.value?.length === 0){
      this.editForm.get(['subSubjectMaster'])!.setValue({ });
    }
    if(this.editForm.get(['topicMaster'])!.value?.length === 0){
      this.editForm.get(['topicMaster'])!.setValue({ });
    }
    if(this.editForm.get(['subTopicMaster'])!.value?.length === 0){
      this.editForm.get(['subTopicMaster'])!.setValue({ });
    }

    this.editForm.patchValue({
      id: examMaster.id,
      title: examMaster.title,
      startDate: examMaster.startDate,
      collegeMaster: examMaster.collegeMaster,
      departmentMaster: examMaster.departmentMaster,
      courseMaster: examMaster.courseMaster,
      categoryMaster: examMaster.categoryMaster,
      subCategoryMaster: examMaster.subCategoryMaster,
      subjectMaster: examMaster.subjectMaster,
      subSubjectMaster: examMaster.subSubjectMaster,
      topicMaster: examMaster.topicMaster,
      subTopicMaster: examMaster.subTopicMaster,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examMaster = this.createFromForm();
    if (examMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.examMasterService.update(examMaster));
    } else {
      this.subscribeToSaveResponse(this.examMasterService.create(examMaster));
    }
  }

  private createFromForm(): IExamMaster {
    if(this.editForm.get(['subjectMaster'])!.value.length === 0){
      this.editForm.get(['subjectMaster'])!.setValue({ });
    }
    if(this.editForm.get(['subSubjectMaster'])!.value.length === 0){
      this.editForm.get(['subSubjectMaster'])!.setValue({ });
    }
    if(this.editForm.get(['topicMaster'])!.value.length === 0){
      this.editForm.get(['topicMaster'])!.setValue({ });
    }
    if(this.editForm.get(['subTopicMaster'])!.value.length === 0){
      this.editForm.get(['subTopicMaster'])!.setValue({ });
    }
    return {
      ...new ExamMaster(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamMaster>>): void {
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
