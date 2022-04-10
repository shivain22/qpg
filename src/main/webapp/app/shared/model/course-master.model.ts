import { IDepartmentMaster } from 'app/shared/model/department-master.model';
import { ICategoryMaster } from 'app/shared/model/category-master.model';

export interface ICourseMaster {
  id?: number;
  name?: string;
  departmentMaster?: IDepartmentMaster;
  categoryMasters?: ICategoryMaster[];
}

export class CourseMaster implements ICourseMaster {
  constructor(
    public id?: number,
    public name?: string,
    public departmentMaster?: IDepartmentMaster,
    public categoryMasters?: ICategoryMaster[]
  ) {}
}
