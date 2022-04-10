import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { AnswerMasterUpdateComponent } from 'app/entities/answer-master/answer-master-update.component';
import { AnswerMasterService } from 'app/entities/answer-master/answer-master.service';
import { AnswerMaster } from 'app/shared/model/answer-master.model';

describe('Component Tests', () => {
  describe('AnswerMaster Management Update Component', () => {
    let comp: AnswerMasterUpdateComponent;
    let fixture: ComponentFixture<AnswerMasterUpdateComponent>;
    let service: AnswerMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [AnswerMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnswerMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnswerMaster(123);
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
        const entity = new AnswerMaster();
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
