import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionBluePrintDetailsUpdateComponent } from 'app/entities/question-blue-print-details/question-blue-print-details-update.component';
import { QuestionBluePrintDetailsService } from 'app/entities/question-blue-print-details/question-blue-print-details.service';
import { QuestionBluePrintDetails } from 'app/shared/model/question-blue-print-details.model';

describe('Component Tests', () => {
  describe('QuestionBluePrintDetail Management Update Component', () => {
    let comp: QuestionBluePrintDetailsUpdateComponent;
    let fixture: ComponentFixture<QuestionBluePrintDetailsUpdateComponent>;
    let service: QuestionBluePrintDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionBluePrintDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionBluePrintDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionBluePrintDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionBluePrintDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionBluePrintDetails(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionBluePrintDetails();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
