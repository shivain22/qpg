import { IQuestionBluePrintDetail } from './question-blue-print-details.model';

export interface IQuestionBluePrintMaster {
  id?: number;
  name?: string;
  description?: string;
  totalMarks?: number;
  questionBluePrintDetails?: IQuestionBluePrintDetail[];
  bpMasterLabel?: string;
  partaDuration?: number;
  partbDuration?: number;
}

export class QuestionBluePrintMaster implements IQuestionBluePrintMaster {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public questionBluePrintDetails?: IQuestionBluePrintDetail[],
    public totalMarks?: number,
    public bpMasterLabel?: string,
    public partaDuration?: number,
    public partbDuration?: number
  ) {}
}
