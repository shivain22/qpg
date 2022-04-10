import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { QpgTestModule } from '../../../test.module';
import { QpBankUploadMasterDetailComponent } from 'app/entities/qp-bank-upload-master/qp-bank-upload-master-detail.component';
import { QpBankUploadMaster } from 'app/shared/model/qp-bank-upload-master.model';

describe('Component Tests', () => {
  describe('QpBankUploadMaster Management Detail Component', () => {
    let comp: QpBankUploadMasterDetailComponent;
    let fixture: ComponentFixture<QpBankUploadMasterDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ qpBankUploadMaster: new QpBankUploadMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QpBankUploadMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QpBankUploadMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QpBankUploadMasterDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load qpBankUploadMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qpBankUploadMaster).toEqual(jasmine.objectContaining({ id: 123 }));
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
