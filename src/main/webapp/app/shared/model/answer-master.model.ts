import { IQuestionMaster } from 'app/shared/model/question-master.model';

export interface IAnswerMaster {
  id?: number;
  text?: string;
  correct?: boolean;
  questionMaster?: IQuestionMaster;
}

export class AnswerMaster implements IAnswerMaster {
  constructor(public id?: number, public text?: string, public correct?: boolean, public questionMaster?: IQuestionMaster) {
    this.correct = this.correct || false;
  }
}
