import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { QpgTestModule } from '../../../test.module';
import { QuestionBankMasterDetailComponent } from 'app/entities/question-bank-master/question-bank-master-detail.component';
import { QuestionBankMaster } from 'app/shared/model/question-bank-master.model';

describe('Component Tests', () => {
  describe('QuestionBankMaster Management Detail Component', () => {
    let comp: QuestionBankMasterDetailComponent;
    let fixture: ComponentFixture<QuestionBankMasterDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ questionBankMaster: new QuestionBankMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionBankMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionBankMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionBankMasterDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load questionBankMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionBankMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
