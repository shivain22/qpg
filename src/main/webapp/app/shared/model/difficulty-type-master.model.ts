import { IQuestionMaster } from 'app/shared/model/question-master.model';

export interface IDifficultyTypeMaster {
  id?: number;
  name?: string;
  questionMasters?: IQuestionMaster[];
}

export class DifficultyTypeMaster implements IDifficultyTypeMaster {
  constructor(public id?: number, public name?: string, public questionMasters?: IQuestionMaster[]) {}
}
