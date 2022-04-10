import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionMasterDetailComponent } from 'app/entities/question-master/question-master-detail.component';
import { QuestionMaster } from 'app/shared/model/question-master.model';

describe('Component Tests', () => {
  describe('QuestionMaster Management Detail Component', () => {
    let comp: QuestionMasterDetailComponent;
    let fixture: ComponentFixture<QuestionMasterDetailComponent>;
    const route = ({ data: of({ questionMaster: new QuestionMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
