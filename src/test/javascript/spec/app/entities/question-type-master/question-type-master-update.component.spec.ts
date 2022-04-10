import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionTypeMasterUpdateComponent } from 'app/entities/question-type-master/question-type-master-update.component';
import { QuestionTypeMasterService } from 'app/entities/question-type-master/question-type-master.service';
import { QuestionTypeMaster } from 'app/shared/model/question-type-master.model';

describe('Component Tests', () => {
  describe('QuestionTypeMaster Management Update Component', () => {
    let comp: QuestionTypeMasterUpdateComponent;
    let fixture: ComponentFixture<QuestionTypeMasterUpdateComponent>;
    let service: QuestionTypeMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionTypeMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionTypeMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionTypeMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionTypeMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionTypeMaster(123);
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
        const entity = new QuestionTypeMaster();
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
