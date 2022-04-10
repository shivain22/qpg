import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionTypeMasterDetailComponent } from 'app/entities/question-type-master/question-type-master-detail.component';
import { QuestionTypeMaster } from 'app/shared/model/question-type-master.model';

describe('Component Tests', () => {
  describe('QuestionTypeMaster Management Detail Component', () => {
    let comp: QuestionTypeMasterDetailComponent;
    let fixture: ComponentFixture<QuestionTypeMasterDetailComponent>;
    const route = ({ data: of({ questionTypeMaster: new QuestionTypeMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionTypeMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionTypeMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionTypeMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionTypeMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionTypeMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
