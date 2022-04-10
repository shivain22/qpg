import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IQbMaster, QbMaster } from 'app/shared/model/qb-master.model';
import { QbMasterService } from './qb-master.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICollegeMaster } from 'app/shared/model/college-master.model';
import { IDepartmentMaster } from 'app/shared/model/department-master.model';
import { ICourseMaster } from 'app/shared/model/course-master.model';
import { ICategoryMaster } from 'app/shared/model/category-master.model';
import { ISubCategoryMaster } from 'app/shared/model/sub-category-master.model';
import { ISubjectMaster } from 'app/shared/model/subject-master.model';
import { ISubSubjectMaster } from 'app/shared/model/sub-subject-master.model';
import { ITopicMaster } from 'app/shared/model/topic-master.model';
import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';
import { CollegeMasterService } from 'app/entities/college-master/college-master.service';
import { QuestionBankMasterService } from 'app/entities/question-bank-master/question-bank-master.service';
import { DepartmentMasterService } from 'app/entities/department-master/department-master.service';
import { CourseMasterService } from 'app/entities/course-master/course-master.service';
import { CategoryMasterService } from 'app/entities/category-master/category-master.service';
import { SubCategoryMasterService } from 'app/entities/sub-category-master/sub-category-master.service';
import { SubjectMasterService } from 'app/entities/subject-master/subject-master.service';
import { SubSubjectMasterService } from 'app/entities/sub-subject-master/sub-subject-master.service';
import { TopicMasterService } from 'app/entities/topic-master/topic-master.service';
import { SubTopicMasterService } from 'app/entities/sub-topic-master/sub-topic-master.service';

@Component({
  selector: 'jhi-qb-master-update',
  templateUrl: './qb-master-update.component.html',
})
export class QbMasterUpdateComponent implements OnInit {
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

  editForm = this.fb.group({
    id: [],
    qbFile: [null, [Validators.required]],
    qbFileContentType: [],
    collegeMaster: [null, Validators.required],
    departmentMaster: [null, Validators.required],
    courseMaster: [null, Validators.required],
    categoryMaster: [null, Validators.required],
    subCategoryMaster: [null, Validators.required],
    subjectMaster: [null, Validators.required],
    subSubjectMaster: [null, Validators.required],
    topicMaster: [null, Validators.required],
    subTopicMaster: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected qbMasterService: QbMasterService,
    protected activatedRoute: ActivatedRoute,
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
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qbMaster }) => {
      this.updateForm(qbMaster);
      this.collegeMasterService.query().subscribe((res: HttpResponse<ICollegeMaster[]>) => (this.collegemasters = res.body || []));
      this.departmentMasterService.query().subscribe((res: HttpResponse<IDepartmentMaster[]>) => (this.departmentmasters = res.body || []));
      this.courseMasterService.query().subscribe((res: HttpResponse<ICourseMaster[]>) => (this.coursemasters = res.body || []));
      this.categoryMasterService.query().subscribe((res: HttpResponse<ICategoryMaster[]>) => (this.categorymasters = res.body || []));
      this.subCategoryMasterService
        .query()
        .subscribe((res: HttpResponse<ISubCategoryMaster[]>) => (this.subcategorymasters = res.body || []));
      this.subjectMasterService.query().subscribe((res: HttpResponse<ISubjectMaster[]>) => (this.subjectmasters = res.body || []));
      this.subSubjectMasterService.query().subscribe((res: HttpResponse<ISubSubjectMaster[]>) => (this.subsubjectmasters = res.body || []));
      this.topicMasterService.query().subscribe((res: HttpResponse<ITopicMaster[]>) => (this.topicmasters = res.body || []));
      this.subTopicMasterService.query().subscribe((res: HttpResponse<ISubTopicMaster[]>) => (this.subtopicmasters = res.body || []));
    });
  }

  updateForm(qbMaster: IQbMaster): void {
    this.editForm.patchValue({
      id: qbMaster.id,
      qbFile: qbMaster.qbFile,
      qbFileContentType: qbMaster.qbFileContentType,
      collegeMaster: qbMaster.collegeMaster,
      departmentMaster: qbMaster.departmentMaster,
      courseMaster: qbMaster.courseMaster,
      categoryMaster: qbMaster.categoryMaster,
      subCategoryMaster: qbMaster.subCategoryMaster,
      subjectMaster: qbMaster.subjectMaster,
      subSubjectMaster: qbMaster.subSubjectMaster,
      topicMaster: qbMaster.topicMaster,
      subTopicMaster: qbMaster.subTopicMaster,
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
    const qbMaster = this.createFromForm();
    if (qbMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.qbMasterService.update(qbMaster));
    } else {
      this.subscribeToSaveResponse(this.qbMasterService.create(qbMaster));
    }
  }

  private createFromForm(): IQbMaster {
    return {
      ...new QbMaster(),
      id: this.editForm.get(['id'])!.value,
      qbFileContentType: this.editForm.get(['qbFileContentType'])!.value,
      qbFile: this.editForm.get(['qbFile'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQbMaster>>): void {
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
