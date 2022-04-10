import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { QpgTestModule } from '../../../test.module';
import { QbMasterDetailComponent } from 'app/entities/qb-master/qb-master-detail.component';
import { QbMaster } from 'app/shared/model/qb-master.model';

describe('Component Tests', () => {
  describe('QbMaster Management Detail Component', () => {
    let comp: QbMasterDetailComponent;
    let fixture: ComponentFixture<QbMasterDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ qbMaster: new QbMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QbMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QbMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QbMasterDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load qbMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qbMaster).toEqual(jasmine.objectContaining({ id: 123 }));
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
