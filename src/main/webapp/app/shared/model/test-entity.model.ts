export interface ITestEntity {
  id?: number;
  testFileContentType?: string;
  testFile?: any;
  fileName?: string;
}

export class TestEntity implements ITestEntity {
  constructor(public id?: number, public testFileContentType?: string, public testFile?: any, public fileName?: string) {}
}
