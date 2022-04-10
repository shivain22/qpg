import { ICollegeMaster } from 'src/main/webapp/app/shared/model/college-master.model';
import { IDepartmentMaster } from 'src/main/webapp/app/shared/model/department-master.model';
import { ICourseMaster } from 'src/main/webapp/app/shared/model/course-master.model';
import { ICategoryMaster } from 'src/main/webapp/app/shared/model/category-master.model';
import { ISubCategoryMaster } from 'src/main/webapp/app/shared/model/sub-category-master.model';
import { ISubjectMaster } from 'src/main/webapp/app/shared/model/subject-master.model';
import { ISubSubjectMaster } from 'src/main/webapp/app/shared/model/sub-subject-master.model';
import { ITopicMaster } from 'src/main/webapp/app/shared/model/topic-master.model';
import { ISubTopicMaster } from 'src/main/webapp/app/shared/model/sub-topic-master.model';

export interface IQuestionBankMaster {
  id?: number;
  questionBankFileContentType?: string;
  questionBankFile?: any;
  collegeMaster?: ICollegeMaster;
  departmentMaster?: IDepartmentMaster;
  courseMaster?: ICourseMaster;
  categoryMaster?: ICategoryMaster;
  subCategoryMaster?: ISubCategoryMaster;
  subjectMaster?: ISubjectMaster;
  subSubjectMaster?: ISubSubjectMaster;
  topicMaster?: ITopicMaster;
  subTopicMaster?: ISubTopicMaster;
}

export class QuestionBankMaster implements IQuestionBankMaster {
  constructor(
    public id?: number,
    public questionBankFileContentType?: string,
    public questionBankFile?: any,
    public collegeMaster?: ICollegeMaster,
    public departmentMaster?: IDepartmentMaster,
    public courseMaster?: ICourseMaster,
    public categoryMaster?: ICategoryMaster,
    public subCategoryMaster?: ISubCategoryMaster,
    public subjectMaster?: ISubjectMaster,
    public subSubjectMaster?: ISubSubjectMaster,
    public topicMaster?: ITopicMaster,
    public subTopicMaster?: ISubTopicMaster
  ) {}
}
