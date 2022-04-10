import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';

export interface IQpBankUploadMaster {
  id?: number;
  qpBankFileContentType?: string;
  qpBankFile?: any;
  name?: string;
  subTopicMaster?: ISubTopicMaster;
}

export class QpBankUploadMaster implements IQpBankUploadMaster {
  constructor(
    public id?: number,
    public qpBankFileContentType?: string,
    public qpBankFile?: any,
    public name?: string,
    public subTopicMaster?: ISubTopicMaster
  ) {}
}
