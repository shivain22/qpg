import { IQuestionMaster } from 'app/shared/model/question-master.model';
import { IQuestionTypeCategoryMaster } from './question-type-category-master.model';

export interface IQuestionTypeMaster {
  id?: number;
  name?: string;
  shortName?: string;
  questionMasters?: IQuestionMaster[];
  defaultWeightage?: number;
  questionTypeCategoryMaster?: IQuestionTypeCategoryMaster;
}

export class QuestionTypeMaster implements IQuestionTypeMaster {
  constructor(
    public id?: number,
    public name?: string,
    public shortName?: string,
    public questionMasters?: IQuestionMaster[],
    public defaultWeightage?: number,
    public questionTypeCategoryMaster?: IQuestionTypeCategoryMaster
  ) {}
}
