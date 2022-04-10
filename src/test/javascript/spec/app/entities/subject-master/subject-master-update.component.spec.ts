import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubjectMasterUpdateComponent } from 'app/entities/subject-master/subject-master-update.component';
import { SubjectMasterService } from 'app/entities/subject-master/subject-master.service';
import { SubjectMaster } from 'app/shared/model/subject-master.model';

describe('Component Tests', () => {
  describe('SubjectMaster Management Update Component', () => {
    let comp: SubjectMasterUpdateComponent;
    let fixture: ComponentFixture<SubjectMasterUpdateComponent>;
    let service: SubjectMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubjectMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SubjectMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubjectMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubjectMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubjectMaster(123);
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
        const entity = new SubjectMaster();
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
