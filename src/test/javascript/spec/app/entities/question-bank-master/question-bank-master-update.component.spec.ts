import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionBankMasterUpdateComponent } from 'app/entities/question-bank-master/question-bank-master-update.component';
import { QuestionBankMasterService } from 'app/entities/question-bank-master/question-bank-master.service';
import { QuestionBankMaster } from 'app/shared/model/question-bank-master.model';

describe('Component Tests', () => {
  describe('QuestionBankMaster Management Update Component', () => {
    let comp: QuestionBankMasterUpdateComponent;
    let fixture: ComponentFixture<QuestionBankMasterUpdateComponent>;
    let service: QuestionBankMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionBankMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionBankMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionBankMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionBankMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionBankMaster(123);
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
        const entity = new QuestionBankMaster();
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
