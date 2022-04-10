import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionBluePrintDetailsDetailComponent } from 'app/entities/question-blue-print-details/question-blue-print-details-detail.component';
import { QuestionBluePrintDetails } from 'app/shared/model/question-blue-print-details.model';

describe('Component Tests', () => {
  describe('QuestionBluePrintDetail Management Detail Component', () => {
    let comp: QuestionBluePrintDetailsDetailComponent;
    let fixture: ComponentFixture<QuestionBluePrintDetailsDetailComponent>;
    const route = ({ data: of({ questionBluePrintDetails: new QuestionBluePrintDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionBluePrintDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionBluePrintDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionBluePrintDetailsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionBluePrintDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionBluePrintDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
