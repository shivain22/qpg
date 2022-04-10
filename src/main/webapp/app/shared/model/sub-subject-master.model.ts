import { ISubjectMaster } from 'app/shared/model/subject-master.model';
import { ITopicMaster } from 'app/shared/model/topic-master.model';

export interface ISubSubjectMaster {
  id?: number;
  name?: string;
  subjectMaster?: ISubjectMaster;
  topicMasters?: ITopicMaster[];
}

export class SubSubjectMaster implements ISubSubjectMaster {
  constructor(public id?: number, public name?: string, public subjectMaster?: ISubjectMaster, public topicMasters?: ITopicMaster[]) {}
}
