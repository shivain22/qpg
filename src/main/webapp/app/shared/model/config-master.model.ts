export interface IConfigMaster {
  id?: number;
  name?: string;
  value?: string;
}

export class ConfigMaster implements IConfigMaster {
  constructor(public id?: number, public name?: string, public value?: string) {}
}
