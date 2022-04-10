import { Moment } from 'moment';
import { IQuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';
import { ICollegeMaster } from './college-master.model';
import { IDepartmentMaster } from './department-master.model';
import { ICourseMaster } from './course-master.model';
import { ICategoryMaster } from './category-master.model';
import { ISubCategoryMaster } from './sub-category-master.model';
import { ISubjectMaster } from './subject-master.model';
import { ISubSubjectMaster } from './sub-subject-master.model';
import { ITopicMaster } from './topic-master.model';
import { ISubTopicMaster } from './sub-topic-master.model';

export interface IExamMaster {
  id?: number;
  title?: string;
  startDate?: Moment;
  collegeMaster?: ICollegeMaster;
  departmentMaster?: IDepartmentMaster;
  courseMaster?: ICourseMaster;
  categoryMaster?: ICategoryMaster;
  subCategoryMaster?: ISubCategoryMaster;
  subjectMaster?: ISubjectMaster;
  subSubjectMaster?: ISubSubjectMaster;
  topicMaster?: ITopicMaster;
  subTopicMaster?: ISubTopicMaster;
  questionBluePrintMaster?: IQuestionBluePrintMaster;
}

export class ExamMaster implements IExamMaster {
  constructor(
    public id?: number,
    public title?: string,
    public startDate?: Moment,
    public collegeMaster?: ICollegeMaster,
    public departmentMaster?: IDepartmentMaster,
    public courseMaster?: ICourseMaster,
    public categoryMaster?: ICategoryMaster,
    public subCategoryMaster?: ISubCategoryMaster,
    public subjectMaster?: ISubjectMaster,
    public subSubjectMaster?: ISubSubjectMaster,
    public topicMaster?: ITopicMaster,
    public subTopicMaster?: ISubTopicMaster,
    public questionBluePrintMaster?: IQuestionBluePrintMaster
  ) {}
}
