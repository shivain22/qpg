import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionBluePrintMasterDetailComponent } from 'app/entities/question-blue-print-master/question-blue-print-master-detail.component';
import { QuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';

describe('Component Tests', () => {
  describe('QuestionBluePrintMaster Management Detail Component', () => {
    let comp: QuestionBluePrintMasterDetailComponent;
    let fixture: ComponentFixture<QuestionBluePrintMasterDetailComponent>;
    const route = ({ data: of({ questionBluePrintMaster: new QuestionBluePrintMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionBluePrintMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionBluePrintMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionBluePrintMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionBluePrintMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionBluePrintMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
