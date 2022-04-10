import { ICollegeMaster } from 'app/shared/model/college-master.model';
import { ICourseMaster } from 'app/shared/model/course-master.model';

export interface IDepartmentMaster {
  id?: number;
  name?: string;
  collegeMaster?: ICollegeMaster;
  courseMasters?: ICourseMaster[];
}

export class DepartmentMaster implements IDepartmentMaster {
  constructor(public id?: number, public name?: string, public collegeMaster?: ICollegeMaster, public courseMasters?: ICourseMaster[]) {}
}
