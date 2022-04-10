import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { QpgTestModule } from '../../../test.module';
import { TestEntityDetailComponent } from 'app/entities/test-entity/test-entity-detail.component';
import { TestEntity } from 'app/shared/model/test-entity.model';

describe('Component Tests', () => {
  describe('TestEntity Management Detail Component', () => {
    let comp: TestEntityDetailComponent;
    let fixture: ComponentFixture<TestEntityDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ testEntity: new TestEntity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [TestEntityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TestEntityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TestEntityDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load testEntity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.testEntity).toEqual(jasmine.objectContaining({ id: 123 }));
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
