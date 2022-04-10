import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionBluePrintMasterUpdateComponent } from 'app/entities/question-blue-print-master/question-blue-print-master-update.component';
import { QuestionBluePrintMasterService } from 'app/entities/question-blue-print-master/question-blue-print-master.service';
import { QuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';

describe('Component Tests', () => {
  describe('QuestionBluePrintMaster Management Update Component', () => {
    let comp: QuestionBluePrintMasterUpdateComponent;
    let fixture: ComponentFixture<QuestionBluePrintMasterUpdateComponent>;
    let service: QuestionBluePrintMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionBluePrintMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionBluePrintMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionBluePrintMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionBluePrintMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionBluePrintMaster(123);
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
        const entity = new QuestionBluePrintMaster();
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
