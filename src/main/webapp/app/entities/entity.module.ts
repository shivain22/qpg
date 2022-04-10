import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category-master',
        loadChildren: () => import('./category-master/category-master.module').then(m => m.QpgCategoryMasterModule),
      },
      {
        path: 'sub-category-master',
        loadChildren: () => import('./sub-category-master/sub-category-master.module').then(m => m.QpgSubCategoryMasterModule),
      },
      {
        path: 'subject-master',
        loadChildren: () => import('./subject-master/subject-master.module').then(m => m.QpgSubjectMasterModule),
      },
      {
        path: 'sub-subject-master',
        loadChildren: () => import('./sub-subject-master/sub-subject-master.module').then(m => m.QpgSubSubjectMasterModule),
      },
      {
        path: 'topic-master',
        loadChildren: () => import('./topic-master/topic-master.module').then(m => m.QpgTopicMasterModule),
      },
      {
        path: 'sub-topic-master',
        loadChildren: () => import('./sub-topic-master/sub-topic-master.module').then(m => m.QpgSubTopicMasterModule),
      },
      {
        path: 'question-master',
        loadChildren: () => import('./question-master/question-master.module').then(m => m.QpgQuestionMasterModule),
      },
      {
        path: 'question-type-master',
        loadChildren: () => import('./question-type-master/question-type-master.module').then(m => m.QpgQuestionTypeMasterModule),
      },
      {
        path: 'question-type-category-master',
        loadChildren: () =>
          import('./question-type-category-master/question-type-category-master.module').then(m => m.QpgQuestionTypeCategoryMasterModule),
      },
      {
        path: 'question-choice-master',
        loadChildren: () => import('./question-choice-master/question-choice-master.module').then(m => m.QpgQuestionChoiceMasterModule),
      },
      {
        path: 'difficulty-type-master',
        loadChildren: () => import('./difficulty-type-master/difficulty-type-master.module').then(m => m.QpgDifficultyTypeMasterModule),
      },
      {
        path: 'answer-master',
        loadChildren: () => import('./answer-master/answer-master.module').then(m => m.QpgAnswerMasterModule),
      },
      {
        path: 'exam-master',
        loadChildren: () => import('./exam-master/exam-master.module').then(m => m.QpgExamMasterModule),
      },
      {
        path: 'question-blue-print-master',
        loadChildren: () =>
          import('./question-blue-print-master/question-blue-print-master.module').then(m => m.QpgQuestionBluePrintMasterModule),
      },
      {
        path: 'question-bank-master',
        loadChildren: () => import('./question-bank-master/question-bank-master.module').then(m => m.QpgQuestionBankMasterModule),
      },
      {
        path: 'college-master',
        loadChildren: () => import('./college-master/college-master.module').then(m => m.QpgCollegeMasterModule),
      },
      {
        path: 'config-master',
        loadChildren: () => import('./config-master/config-master.module').then(m => m.QpgConfigMasterModule),
      },
      {
        path: 'department-master',
        loadChildren: () => import('./department-master/department-master.module').then(m => m.QpgDepartmentMasterModule),
      },
      {
        path: 'course-master',
        loadChildren: () => import('./course-master/course-master.module').then(m => m.QpgCourseMasterModule),
      },
      {
        path: 'test-entity',
        loadChildren: () => import('./test-entity/test-entity.module').then(m => m.QpgTestEntityModule),
      },
      {
        path: 'qb-master',
        loadChildren: () => import('./qb-master/qb-master.module').then(m => m.QpgQbMasterModule),
      },
      {
        path: 'qp-bank-upload-master',
        loadChildren: () => import('./qp-bank-upload-master/qp-bank-upload-master.module').then(m => m.QpgQpBankUploadMasterModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class QpgEntityModule {}
