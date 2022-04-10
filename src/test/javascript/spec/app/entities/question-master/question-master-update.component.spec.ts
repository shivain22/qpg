import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { QuestionMasterUpdateComponent } from 'app/entities/question-master/question-master-update.component';
import { QuestionMasterService } from 'app/entities/question-master/question-master.service';
import { QuestionMaster } from 'app/shared/model/question-master.model';

describe('Component Tests', () => {
  describe('QuestionMaster Management Update Component', () => {
    let comp: QuestionMasterUpdateComponent;
    let fixture: ComponentFixture<QuestionMasterUpdateComponent>;
    let service: QuestionMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [QuestionMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionMaster(123);
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
        const entity = new QuestionMaster();
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
