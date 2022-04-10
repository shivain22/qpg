import { ISubCategoryMaster } from 'app/shared/model/sub-category-master.model';

export interface ISubjectMaster {
  id?: number;
  name?: string;
  subCategoryMaster?: ISubCategoryMaster;
}

export class SubjectMaster implements ISubjectMaster {
  constructor(public id?: number, public name?: string, public subCategoryMaster?: ISubCategoryMaster) {}
}
