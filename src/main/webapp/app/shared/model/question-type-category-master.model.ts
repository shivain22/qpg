export interface IQuestionTypeCategoryMaster {
  id?: number;
  name?: string;
  shortName?: string;
  defaultWeightage?: number;
}

export class QuestionTypeCategoryMaster implements IQuestionTypeCategoryMaster {
  constructor(public id?: number, public name?: string, public shortName?: string, public defaultWeightage?: number) {}
}
