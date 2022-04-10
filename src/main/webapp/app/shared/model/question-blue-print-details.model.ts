import { IQuestionTypeMaster } from 'app/shared/model/question-type-master.model';
import { IQuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';
import { IDifficultyTypeMaster } from './difficulty-type-master.model';
import { IQuestionChoiceMaster } from './question-choice-master.model';

export interface IQuestionBluePrintDetail {
  id?: number;
  totalQuestions?: number;
  questionTypeMaster?: IQuestionTypeMaster;
  questionBluePrintMaster?: IQuestionBluePrintMaster;
  numOfChoices?: number;
  questionChoiceMaster?: IQuestionChoiceMaster;
  difficultyTypeMaster?: IDifficultyTypeMaster;
}

export class QuestionBluePrintDetail implements IQuestionBluePrintDetail {
  constructor(
    public id?: number,
    public totalQuestions?: number,
    public questionTypeMaster?: IQuestionTypeMaster,
    public questionBluePrintMaster?: IQuestionBluePrintMaster,
    public numOfChoices?: number,
    public questionChoiceMaster?: IQuestionChoiceMaster,
    public difficultyTypeMaster?: IDifficultyTypeMaster
  ) {}
}
