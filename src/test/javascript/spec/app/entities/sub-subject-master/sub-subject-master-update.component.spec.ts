import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubSubjectMasterUpdateComponent } from 'app/entities/sub-subject-master/sub-subject-master-update.component';
import { SubSubjectMasterService } from 'app/entities/sub-subject-master/sub-subject-master.service';
import { SubSubjectMaster } from 'app/shared/model/sub-subject-master.model';

describe('Component Tests', () => {
  describe('SubSubjectMaster Management Update Component', () => {
    let comp: SubSubjectMasterUpdateComponent;
    let fixture: ComponentFixture<SubSubjectMasterUpdateComponent>;
    let service: SubSubjectMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubSubjectMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SubSubjectMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubSubjectMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubSubjectMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubSubjectMaster(123);
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
        const entity = new SubSubjectMaster();
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
