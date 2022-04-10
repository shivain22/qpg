import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { QpgTestModule } from '../../../test.module';
import { QuestionBluePrintDetailsComponent } from 'app/entities/question-blue-print-details/question-blue-print-details.component';
import { QuestionBluePrintDetailsService } from 'app/entities/question-blue-print-details/question-blue-print-details.service';
import { QuestionBluePrintDetails } from 'app/shared/model/question-blue-print-details.model';

describe('Component Tests', () => {
  describe('QuestionBluePrintDetail Management Component', () => {
    let comp: QuestionBluePrintDetailsComponent;
    let fixture: ComponentFixture<QuestionBluePrintDetailsComponent>;
    let service: QuestionBluePrintDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionBluePrintDetailsComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(QuestionBluePrintDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionBluePrintDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionBluePrintDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionBluePrintDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionBluePrintDetails && comp.questionBluePrintDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionBluePrintDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionBluePrintDetails && comp.questionBluePrintDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
