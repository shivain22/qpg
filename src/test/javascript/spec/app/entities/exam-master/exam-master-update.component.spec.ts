import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { ExamMasterUpdateComponent } from 'app/entities/exam-master/exam-master-update.component';
import { ExamMasterService } from 'app/entities/exam-master/exam-master.service';
import { ExamMaster } from 'app/shared/model/exam-master.model';

describe('Component Tests', () => {
  describe('ExamMaster Management Update Component', () => {
    let comp: ExamMasterUpdateComponent;
    let fixture: ComponentFixture<ExamMasterUpdateComponent>;
    let service: ExamMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [ExamMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExamMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExamMaster(123);
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
        const entity = new ExamMaster();
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
