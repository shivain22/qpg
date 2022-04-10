import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { DepartmentMasterUpdateComponent } from 'app/entities/department-master/department-master-update.component';
import { DepartmentMasterService } from 'app/entities/department-master/department-master.service';
import { DepartmentMaster } from 'app/shared/model/department-master.model';

describe('Component Tests', () => {
  describe('DepartmentMaster Management Update Component', () => {
    let comp: DepartmentMasterUpdateComponent;
    let fixture: ComponentFixture<DepartmentMasterUpdateComponent>;
    let service: DepartmentMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [DepartmentMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DepartmentMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DepartmentMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DepartmentMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DepartmentMaster(123);
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
        const entity = new DepartmentMaster();
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
