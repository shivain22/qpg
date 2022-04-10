import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { AnswerMasterDetailComponent } from 'app/entities/answer-master/answer-master-detail.component';
import { AnswerMaster } from 'app/shared/model/answer-master.model';

describe('Component Tests', () => {
  describe('AnswerMaster Management Detail Component', () => {
    let comp: AnswerMasterDetailComponent;
    let fixture: ComponentFixture<AnswerMasterDetailComponent>;
    const route = ({ data: of({ answerMaster: new AnswerMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [AnswerMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnswerMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load answerMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.answerMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
