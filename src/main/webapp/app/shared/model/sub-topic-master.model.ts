import { ITopicMaster } from 'app/shared/model/topic-master.model';
import { IQuestionMaster } from 'app/shared/model/question-master.model';

export interface ISubTopicMaster {
  id?: number;
  name?: string;
  topicMaster?: ITopicMaster;
  questionMasters?: IQuestionMaster[];
}

export class SubTopicMaster implements ISubTopicMaster {
  constructor(public id?: number, public name?: string, public topicMaster?: ITopicMaster, public questionMasters?: IQuestionMaster[]) {}
}
