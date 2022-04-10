import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { ExamMasterDetailComponent } from 'app/entities/exam-master/exam-master-detail.component';
import { ExamMaster } from 'app/shared/model/exam-master.model';

describe('Component Tests', () => {
  describe('ExamMaster Management Detail Component', () => {
    let comp: ExamMasterDetailComponent;
    let fixture: ComponentFixture<ExamMasterDetailComponent>;
    const route = ({ data: of({ examMaster: new ExamMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [ExamMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExamMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExamMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load examMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.examMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
