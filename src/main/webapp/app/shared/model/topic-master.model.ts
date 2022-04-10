import { ISubSubjectMaster } from 'app/shared/model/sub-subject-master.model';

export interface ITopicMaster {
  id?: number;
  name?: string;
  shortCode?: string;
  subSubjectMaster?: ISubSubjectMaster;
}

export class TopicMaster implements ITopicMaster {
  constructor(public id?: number, public name?: string, public shortCode?: string, public subSubjectMaster?: ISubSubjectMaster) {}
}
