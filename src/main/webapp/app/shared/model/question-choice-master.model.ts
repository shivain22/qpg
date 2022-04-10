export interface IQuestionChoiceMaster {
  id?: number;
  name?: string;
  shortName?: string;
}

export class QuestionChoiceMaster implements IQuestionChoiceMaster {
  constructor(public id?: number, public name?: string, public shortName?: string) {}
}
