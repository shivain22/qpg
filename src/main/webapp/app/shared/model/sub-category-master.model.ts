import { ISubjectMaster } from 'app/shared/model/subject-master.model';
import { ICategoryMaster } from 'app/shared/model/category-master.model';

export interface ISubCategoryMaster {
  id?: number;
  name?: string;
  subjectMasters?: ISubjectMaster[];
  categoryMaster?: ICategoryMaster;
}

export class SubCategoryMaster implements ISubCategoryMaster {
  constructor(
    public id?: number,
    public name?: string,
    public subjectMasters?: ISubjectMaster[],
    public categoryMaster?: ICategoryMaster
  ) {}
}
