import { ISubCategoryMaster } from 'app/shared/model/sub-category-master.model';
import { ICourseMaster } from 'src/main/webapp/app/shared/model/course-master.model';

export interface ICategoryMaster {
  id?: number;
  name?: string;
  courseMaster?: ICourseMaster;
  subCategoryMasters?: ISubCategoryMaster[];
}

export class CategoryMaster implements ICategoryMaster {
  constructor(
    public id?: number,
    public name?: string,
    public courseMaster?: ICourseMaster,
    public subCategoryMasters?: ISubCategoryMaster[]
  ) {}
}
