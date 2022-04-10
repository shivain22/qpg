import { IDepartmentMaster } from 'app/shared/model/department-master.model';

export interface ICollegeMaster {
  id?: number;
  name?: string;
  departmentMasters?: IDepartmentMaster[];
}

export class CollegeMaster implements ICollegeMaster {
  constructor(public id?: number, public name?: string, public departmentMasters?: IDepartmentMaster[]) {}
}
