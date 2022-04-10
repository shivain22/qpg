import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { QbMasterService } from 'app/entities/qb-master/qb-master.service';
import { IQbMaster, QbMaster } from 'app/shared/model/qb-master.model';

describe('Service Tests', () => {
  describe('QbMaster Service', () => {
    let injector: TestBed;
    let service: QbMasterService;
    let httpMock: HttpTestingController;
    let elemDefault: IQbMaster;
    let expectedResult: IQbMaster | IQbMaster[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(QbMasterService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new QbMaster(
        0,
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a QbMaster', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new QbMaster()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a QbMaster', () => {
        const returnedFromService = Object.assign(
          {
            qbFile: 'BBBBBB',
            collegeMaster: 'BBBBBB',
            departmentMaster: 'BBBBBB',
            courseMaster: 'BBBBBB',
            categoryMaster: 'BBBBBB',
            subCategoryMaster: 'BBBBBB',
            subjectMaster: 'BBBBBB',
            subSubjectMaster: 'BBBBBB',
            topicMaster: 'BBBBBB',
            subTopicMaster: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of QbMaster', () => {
        const returnedFromService = Object.assign(
          {
            qbFile: 'BBBBBB',
            collegeMaster: 'BBBBBB',
            departmentMaster: 'BBBBBB',
            courseMaster: 'BBBBBB',
            categoryMaster: 'BBBBBB',
            subCategoryMaster: 'BBBBBB',
            subjectMaster: 'BBBBBB',
            subSubjectMaster: 'BBBBBB',
            topicMaster: 'BBBBBB',
            subTopicMaster: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a QbMaster', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
