import { IQuestionTypeMaster } from 'app/shared/model/question-type-master.model';
import { IDifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';
import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';
import { IAnswerMaster } from 'app/shared/model/answer-master.model';

export interface IQuestionMaster {
  id?: number;
  text?: string;
  weightage?: number;
  questionTypeMaster?: IQuestionTypeMaster;
  difficultyTypeMaster?: IDifficultyTypeMaster;
  subTopicMaster?: ISubTopicMaster;
  parentQuestionMaster?: IQuestionMaster;
  questionMasters?: IQuestionMaster[];
  answerMasters?: IAnswerMaster[];
  file?: any;
}

export class QuestionMaster implements IQuestionMaster {
  constructor(
    public id?: number,
    public text?: string,
    public weightage?: number,
    public questionTypeMaster?: IQuestionTypeMaster,
    public difficultyTypeMaster?: IDifficultyTypeMaster,
    public subTopicMaster?: ISubTopicMaster,
    public parentQuestionMaster?: IQuestionMaster,
    public questionMasters?: IQuestionMaster[],
    public answerMasters?: IAnswerMaster[],
    public file?: any
  ) {}
}
