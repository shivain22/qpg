import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { CourseMasterUpdateComponent } from 'app/entities/course-master/course-master-update.component';
import { CourseMasterService } from 'app/entities/course-master/course-master.service';
import { CourseMaster } from 'app/shared/model/course-master.model';

describe('Component Tests', () => {
  describe('CourseMaster Management Update Component', () => {
    let comp: CourseMasterUpdateComponent;
    let fixture: ComponentFixture<CourseMasterUpdateComponent>;
    let service: CourseMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [CourseMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CourseMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CourseMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CourseMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CourseMaster(123);
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
        const entity = new CourseMaster();
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
